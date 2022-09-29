package org.knowm.xchange.bitrue;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitrue.dto.account.AssetDetail;
import org.knowm.xchange.bitrue.dto.meta.exchangeinfo.BitrueExchangeInfo;
import org.knowm.xchange.bitrue.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.bitrue.dto.meta.exchangeinfo.Symbol;
import org.knowm.xchange.bitrue.service.BitrueAccountService;
import org.knowm.xchange.bitrue.service.BitrueMarketDataService;
import org.knowm.xchange.bitrue.service.BitrueTradeService;
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

public class BitrueExchange extends BaseExchange {

  protected static ResilienceRegistries RESILIENCE_REGISTRIES;

  protected BitrueExchangeInfo exchangeInfo;
  protected BitrueAuthenticated bitrue;
  protected SynchronizedValueFactory<Long> timestampFactory;

  @Override
  protected void initServices() {
    this.bitrue =
        ExchangeRestProxyBuilder.forInterface(
                BitrueAuthenticated.class, getExchangeSpecification())
            .build();
    this.timestampFactory =
        new BitrueTimestampFactory(
                bitrue, getExchangeSpecification().getResilience(), getResilienceRegistries());
    this.marketDataService = new BitrueMarketDataService(this, bitrue, getResilienceRegistries());
    this.tradeService = new BitrueTradeService(this, bitrue, getResilienceRegistries());
    this.accountService = new BitrueAccountService(this, bitrue, getResilienceRegistries());
  }

  public SynchronizedValueFactory<Long> getTimestampFactory() {
    return timestampFactory;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException(
        "Binance uses timestamp/recvwindow rather than a nonce");
  }

  public static void resetResilienceRegistries() {
    RESILIENCE_REGISTRIES = null;
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = BitrueResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://www.bitrue.com");
    spec.setHost("www.bitrue.com");
    spec.setPort(80);
    spec.setExchangeName("Bitrue");
    spec.setExchangeDescription("Bitrue Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "bitrue");
    return spec;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
  }

  public BitrueExchangeInfo getExchangeInfo() {
    return exchangeInfo;
  }

  @Override
  public void remoteInit() {

    try {
      BitrueMarketDataService marketDataService =
          (BitrueMarketDataService) this.marketDataService;
      exchangeInfo = marketDataService.getExchangeInfo();

      BitrueAccountService accountService = (BitrueAccountService) getAccountService();
      Map<String, AssetDetail> assetDetailMap = null;
      if (isAuthenticated()) {
        assetDetailMap = accountService.getAssetDetails();
      }

      postInit(assetDetailMap);

    } catch (Exception e) {
      throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
    }
  }

  protected void postInit(Map<String, AssetDetail> assetDetailMap) {
    // populate currency pair keys only, exchange does not provide any other metadata for download
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

    // Clear all hardcoded currencies when loading dynamically from exchange.
    if (assetDetailMap != null) {
      currencies.clear();
    }

    Symbol[] symbols = exchangeInfo.getSymbols();

    for (Symbol symbol : symbols) {
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
            pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getPriceScale()));
            counterMaxQty = new BigDecimal(filter.getMaxPrice()).stripTrailingZeros();
          } else if (filter.getFilterType().equals("LOT_SIZE")) {
            if(filter.getVolumeScale() != null) {
              amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getVolumeScale()));
            }
            else{

            }
            minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
            maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();

          } else if (filter.getFilterType().equals("MIN_NOTIONAL")) {
            counterMinQty = new BigDecimal(filter.getMinVal()).stripTrailingZeros();
          }
        }

        boolean marketOrderAllowed = Arrays.asList(symbol.getOrderTypes()).contains("MARKET");
        currencyPairs.put(
            currentCurrencyPair,
            new CurrencyPairMetaData(
                new BigDecimal("0.1"), // Trading fee at Binance is 0.1 %
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
            BitrueAdapters.adaptCurrencyMetaData(
                currencies, baseCurrency, assetDetailMap, basePrecision);
        currencies.put(baseCurrency, baseCurrencyMetaData);

        Currency counterCurrency = currentCurrencyPair.counter;
        CurrencyMetaData counterCurrencyMetaData =
            BitrueAdapters.adaptCurrencyMetaData(
                currencies, counterCurrency, assetDetailMap, counterPrecision);
        currencies.put(counterCurrency, counterCurrencyMetaData);
      }
    }
  }

  private boolean isAuthenticated() {
    return exchangeSpecification != null
        && exchangeSpecification.getApiKey() != null
        && exchangeSpecification.getSecretKey() != null;
  }

  private int numberOfDecimals(String value) {
    return new BigDecimal(value).stripTrailingZeros().scale();
  }



}
