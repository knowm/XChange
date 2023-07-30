package org.knowm.xchange.binance;

import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange implements Exchange {
  public static final String SPECIFIC_PARAM_USE_SANDBOX = "Use_Sandbox";
  public static final String SPECIFIC_PARAM_USE_FUTURES_SANDBOX = "Use_Sandbox_Futures";
  public static final String SPECIFIC_PARAM_FUTURES_ENABLED = "Futures_Enabled";

  private static final String SPOT_URL = "https://api.binance.com";
  public static final String FUTURES_URL = "https://fapi.binance.com";
  public static final String SANDBOX_FUTURES_URL = "https://testnet.binancefuture.com";
  protected static ResilienceRegistries RESILIENCE_REGISTRIES;
  protected SynchronizedValueFactory<Long> timestampFactory;

  @Override
  protected void initServices() {
    this.timestampFactory = new BinanceTimestampFactory(getExchangeSpecification().getResilience(), getResilienceRegistries());
    this.marketDataService = new BinanceMarketDataService(this, getResilienceRegistries());
    this.tradeService = new BinanceTradeService(this, getResilienceRegistries());
    this.accountService = new BinanceAccountService(this, getResilienceRegistries());
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
      RESILIENCE_REGISTRIES = BinanceResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri(SPOT_URL);
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    concludeHostParams(exchangeSpecification);
    super.applySpecification(exchangeSpecification);
  }

  public boolean isFuturesSandbox(){
    return Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_USE_FUTURES_SANDBOX));
  }

  public boolean isFuturesEnabled(){
    return Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_FUTURES_ENABLED));
  }

  public boolean usingSandbox() {
    return enabledSandbox(exchangeSpecification);
  }

  @Override
  public void remoteInit() {

    try {
      BinanceMarketDataService marketDataService =
          (BinanceMarketDataService) this.marketDataService;

      BinanceAccountService accountService = (BinanceAccountService) getAccountService();
      Map<String, AssetDetail> assetDetailMap = null;
      if (!usingSandbox() && isAuthenticated()) {
        assetDetailMap = accountService.getAssetDetails(); // not available in sndbox
      }
      if(usingSandbox()){
        if(isFuturesSandbox()){
          BinanceAdapters.adaptFutureExchangeMetaData(exchangeMetaData, marketDataService.getFutureExchangeInfo());
        } else {
          exchangeMetaData = BinanceAdapters.adaptExchangeMetaData(marketDataService.getExchangeInfo(), assetDetailMap);
        }
      } else {
        exchangeMetaData = BinanceAdapters.adaptExchangeMetaData(marketDataService.getExchangeInfo(), assetDetailMap);
        if(isFuturesEnabled()){
          BinanceAdapters.adaptFutureExchangeMetaData(exchangeMetaData, marketDataService.getFutureExchangeInfo());
        }
      }

    } catch (Exception e) {
      throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
    }
  }

  protected boolean isAuthenticated() {
    return exchangeSpecification != null
        && exchangeSpecification.getApiKey() != null
        && exchangeSpecification.getSecretKey() != null;
  }

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (enabledSandbox(exchangeSpecification)) {
        exchangeSpecification.setSslUri("https://testnet.binance.vision");
        exchangeSpecification.setHost("testnet.binance.vision");
      }
    }
  }

  private static boolean enabledSandbox(ExchangeSpecification exchangeSpecification) {
    return Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_USE_SANDBOX)) ||
            Boolean.TRUE.equals(
                    exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_USE_FUTURES_SANDBOX));
  }

}
