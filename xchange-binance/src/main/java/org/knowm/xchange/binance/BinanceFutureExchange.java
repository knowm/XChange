package org.knowm.xchange.binance;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceFutureAccountService;
import org.knowm.xchange.binance.service.BinanceFutureMarketDataService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import si.mazi.rescu.SynchronizedValueFactory;

import java.math.BigDecimal;
import java.util.Map;

public class BinanceFutureExchange extends BaseExchange {
  protected BinanceFutureAuthenticated binance;
  public static final String SPECIFIC_PARAM_USE_SANDBOX = "Use_Sandbox";
  protected SynchronizedValueFactory<Long> timestampFactory;
  protected static ResilienceRegistries RESILIENCE_REGISTRIES;

  @Override
  protected void initServices() {
    this.binance = ExchangeRestProxyBuilder.forInterface(BinanceFutureAuthenticated.class, getExchangeSpecification()).build();
    this.timestampFactory = new BinanceFutureTimestampFactory(binance, getExchangeSpecification().getResilience(),
            getResilienceRegistries());
    this.marketDataService = new BinanceFutureMarketDataService(this, binance, getResilienceRegistries());
    //        this.tradeService = new BinanceTradeService(this, binance, getResilienceRegistries());
    this.accountService = new BinanceFutureAccountService(this, binance, getResilienceRegistries());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://fapi.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance Future");
    spec.setExchangeDescription("Binance Future Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  public SynchronizedValueFactory<Long> getTimestampFactory() {
    return timestampFactory;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Binance uses timestamp/recvwindow rather than a nonce");
  }

  public static void resetResilienceRegistries() {
    RESILIENCE_REGISTRIES = null;
  }

  /**
   * Helps to provide custom resilence registers, overriding what would be created by default and
   * returned by the {@link #getResilienceRegistries()}.
   *
   * <p>Retained till next call of {@link #resetResilienceRegistries()}.
   */
  public static void setResilienceRegistries(ResilienceRegistries resilienceRegistries) {
    RESILIENCE_REGISTRIES = resilienceRegistries;
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = BinanceFutureResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    concludeHostParams(exchangeSpecification);
    super.applySpecification(exchangeSpecification);
  }


  public boolean usingSandbox() {
    return enabledSandbox(exchangeSpecification);
  }

  @Override
  public void remoteInit() {
    try {
      BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;

      BinanceAccountService accountService = (BinanceAccountService) getAccountService();
      Map<String, AssetDetail> assetDetailMap = null;
      if (!usingSandbox() && isAuthenticated()) {
        assetDetailMap = accountService.getAssetDetails(); // not available in sndbox
      }
      exchangeMetaData = BinanceAdapters.adaptExchangeMetaData(marketDataService.getExchangeInfo(), assetDetailMap);
      BinanceAdapters.adaptFutureExchangeMetaData(exchangeMetaData, marketDataService.getFutureExchangeInfo());
    } catch (Exception e) {
      throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
    }
  }

  private boolean isAuthenticated() {
    return exchangeSpecification != null && exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null;
  }

  private int numberOfDecimals(String value) {
    return new BigDecimal(value).stripTrailingZeros().scale();
  }

  /**
   * Adjust host parameters depending on exchange specific parameters
   */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (enabledSandbox(exchangeSpecification)) {
        exchangeSpecification.setSslUri("https://testnet.binancefuture.com");
        exchangeSpecification.setHost("testnet.binancefuture.com");
      }
    }
  }

  private static boolean enabledSandbox(ExchangeSpecification exchangeSpecification) {
    return Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_USE_SANDBOX));
  }
}
