package org.knowm.xchange.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Symbol;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

  private static final int DEFAULT_PRECISION = 8;

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();
  private BinanceExchangeInfo exchangeInfo;
  private Long deltaServerTime;

  @Override
  protected void initServices() {
    this.marketDataService = new BinanceMarketDataService(this);
    this.tradeService = new BinanceTradeService(this);
    this.accountService = new BinanceAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://api.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  public BinanceExchangeInfo getExchangeInfo() {
    return exchangeInfo;
  }

  @Override
  public void remoteInit() {
    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
      Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

      BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
      exchangeInfo = marketDataService.getExchangeInfo();

      for (Symbol symbol : exchangeInfo.getSymbols()) {
        
        CurrencyPair pair = new CurrencyPair(symbol.getBaseAsset(), symbol.getQuoteAsset());
        CurrencyPairMetaData pairMetaData = currencyPairs.get(pair);
        if (pairMetaData == null) {
          BigDecimal tradingFee = BigDecimal.ZERO;
          BigDecimal minAmount = BigDecimal.ZERO;
          BigDecimal maxAmount = BigDecimal.ZERO;
          Integer priceScale = DEFAULT_PRECISION;
          for (Filter filter : symbol.getFilters()) {
            if (filter.getFilterType().equals("PRICE_FILTER")) {
              priceScale = numberOfDecimals(filter.getTickSize());
            } else if (filter.getFilterType().equals("LOT_SIZE")) {
              minAmount = new BigDecimal(filter.getMinQty());
              maxAmount = new BigDecimal(filter.getMaxQty());
            }
          }
          pairMetaData = new CurrencyPairMetaData(tradingFee, minAmount, maxAmount, priceScale);
          currencyPairs.put(pair, pairMetaData);
        }
        
        CurrencyMetaData baseMetaData = currencies.get(pair.base);
        if (baseMetaData == null) {
          Integer basePrecision = Integer.parseInt(symbol.getBaseAssetPrecision());
          currencies.put(pair.base, new CurrencyMetaData(basePrecision, BigDecimal.ZERO));
        }
        CurrencyMetaData counterMetaData = currencies.get(pair.base);
        if (counterMetaData == null) {
          Integer counterPrecision = Integer.parseInt(symbol.getQuotePrecision());
          currencies.put(pair.counter, new CurrencyMetaData(counterPrecision, BigDecimal.ZERO));
        }
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }

  private int numberOfDecimals(String value) {
    try {
      double d = Double.parseDouble(value);
      String s = new DecimalFormat("#.############").format(d);
      return s.split("\\.")[1].length();
    } catch (ArrayIndexOutOfBoundsException e) {
      return DEFAULT_PRECISION;
    }
  }

  public long deltaServerTime() throws IOException {
    if (deltaServerTime == null) {
      Binance binance = RestProxyFactory.createProxy(Binance.class, getExchangeSpecification().getSslUri());
      deltaServerTime = binance.time().getServerTime().getTime() - System.currentTimeMillis();
    }
    return deltaServerTime;
  }
}
