package org.knowm.xchange.binance;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.*;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Symbol;
import org.knowm.xchange.binance.futures.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.futures.dto.meta.BinanceFuturesExchangeInfo;
import org.knowm.xchange.binance.futures.dto.meta.FuturesSymbol;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesMarketDataService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarginAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import si.mazi.rescu.SynchronizedValueFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class BinanceFuturesExchange extends BinanceExchange {

  @Override
  protected void initServices() {

    this.binance = ExchangeRestProxyBuilder.forInterface(
                    BinanceFuturesAuthenticated.class, getExchangeSpecification())
            .build();
    this.accountService = new BinanceFuturesAccountService(this, binance, getResilienceRegistries());
    this.marketDataService = new BinanceFuturesMarketDataService(this, binance, getResilienceRegistries());
    this.tradeService = new BinanceFuturesTradeService(this, binance, getResilienceRegistries());

    this.timestampFactory =
            new BinanceTimestampFactory(
                    binance, getExchangeSpecification().getResilience(), getResilienceRegistries());
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException(
            "Binance uses timestamp/recvwindow rather than a nonce");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://fapi.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance Futures");
    spec.setExchangeDescription("Binance Futures Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  @Override
  public void remoteInit() {

    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
      Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

      currencies.clear();
      currencyPairs.clear();

      BinanceFuturesMarketDataService marketDataService =
              (BinanceFuturesMarketDataService) this.marketDataService;
      BinanceFuturesExchangeInfo exchangeInfo = marketDataService.getFuturesExchangeInfo();
      FuturesSymbol[] symbols = exchangeInfo.getSymbols();

      for (FuturesSymbol symbol : symbols) {
        if (symbol.getStatus().equals("TRADING")) { // Symbols which are trading
          int basePrecision = Integer.parseInt(symbol.getBaseAssetPrecision());
          int counterPrecision = Integer.parseInt(symbol.getQuotePrecision());
          int pairPrecision = 8;
          int amountPrecision = 8;

          BigDecimal minQty = null;
          BigDecimal maxQty = null;
          BigDecimal stepSize = null;

          BigDecimal counterMinQty = null;
          BigDecimal counterMaxQty = null;

          Filter[] filters = symbol.getFilters();

          CurrencyPair currentCurrencyPair =
                  new CurrencyPair(symbol.getBaseAsset(), symbol.getQuoteAsset());

          for (Filter filter : filters) {
            if (filter.getFilterType().equals("PRICE_FILTER")) {
              pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getTickSize()));
              counterMaxQty = new BigDecimal(filter.getMaxPrice()).stripTrailingZeros();
            } else if (filter.getFilterType().equals("LOT_SIZE")) {
              amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getStepSize()));
              minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
              maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();
              stepSize = new BigDecimal(filter.getStepSize()).stripTrailingZeros();
            } else if (filter.getFilterType().equals("MIN_NOTIONAL")) {
              counterMinQty = new BigDecimal(filter.getNotional()).stripTrailingZeros();
            }
          }

          boolean marketOrderAllowed = Arrays.asList(symbol.getOrderTypes()).contains("MARKET");
          currencyPairs.put(
                  currentCurrencyPair,
                  new CurrencyPairMetaData(
                          new BigDecimal("0.001"), // Trading fee at Binance is 0.1 %
                          minQty, // Min amount
                          maxQty, // Max amount
                          counterMinQty,
                          counterMaxQty,
                          amountPrecision, // base precision
                          pairPrecision, // counter precision
                          null,
                          null, /* TODO get fee tiers, although this is not necessary now
                        because their API returns current fee directly */
                          stepSize,
                          null,
                          marketOrderAllowed));

          Currency baseCurrency = currentCurrencyPair.base;
          CurrencyMetaData baseCurrencyMetaData =
                  BinanceAdapters.adaptCurrencyMetaData(
                          currencies, baseCurrency, null, basePrecision);
          currencies.put(baseCurrency, baseCurrencyMetaData);

          Currency counterCurrency = currentCurrencyPair.counter;
          CurrencyMetaData counterCurrencyMetaData =
                  BinanceAdapters.adaptCurrencyMetaData(
                          currencies, counterCurrency, null, counterPrecision);
          currencies.put(counterCurrency, counterCurrencyMetaData);
        }
      }
    } catch (Exception e) {
      throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
    }
  }
}
