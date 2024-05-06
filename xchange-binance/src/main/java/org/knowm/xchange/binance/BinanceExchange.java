package org.knowm.xchange.binance;

import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange implements Exchange {
  public static String EXCHANGE_TYPE = "Exchange_Type";
  private static final String SPOT_URL = "https://api.binance.com";
  public static final String FUTURES_URL = "https://fapi.binance.com";
  public static final String INVERSE_FUTURES_URL = "https://dapi.binance.com";
  public static final String PORTFOLIO_MARGIN_URL = "https://papi.binance.com";

  public static final String SANDBOX_SPOT_URL = "https://testnet.binance.vision";
  public static final String SANDBOX_FUTURES_URL = "https://testnet.binancefuture.com";
  public static final String SANDBOX_INVERSE_FUTURES_URL = "https://testnet.binancefuture.com";

  protected static ResilienceRegistries RESILIENCE_REGISTRIES;
  protected SynchronizedValueFactory<Long> timestampFactory;

  @Override
  protected void initServices() {
    this.timestampFactory =
        new BinanceTimestampFactory(
            getExchangeSpecification().getResilience(), getResilienceRegistries());
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
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    concludeHostParams(exchangeSpecification);
    super.applySpecification(exchangeSpecification);
  }

  public boolean isFuturesEnabled() {
    return ExchangeType.FUTURES.equals(exchangeSpecification.
        getExchangeSpecificParametersItem(EXCHANGE_TYPE));
  }

  public boolean isPortfolioMarginEnabled() {
    return ExchangeType.PORTFOLIO_MARGIN.equals(exchangeSpecification
        .getExchangeSpecificParametersItem(EXCHANGE_TYPE));
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
      //hook for Binance US
      if(exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE) ==null) {
        exchangeMetaData =
            BinanceAdapters.adaptExchangeMetaData(
                marketDataService.getExchangeInfo(), assetDetailMap);
      } else {
        switch ((ExchangeType) exchangeSpecification.getExchangeSpecificParametersItem(
            EXCHANGE_TYPE)) {
          case SPOT: {
            exchangeMetaData =
                BinanceAdapters.adaptExchangeMetaData(
                    marketDataService.getExchangeInfo(), assetDetailMap);
            break;
          }
          case FUTURES: {
            BinanceAdapters.adaptFutureExchangeMetaData(
                exchangeMetaData, marketDataService.getFutureExchangeInfo());
            break;
          }
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

  /**
   * Adjust host parameters depending on exchange specific parameters
   */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if(exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE) != null) {
      switch ((ExchangeType)exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE)) {
        case SPOT: {
          if (enabledSandbox(exchangeSpecification))
            exchangeSpecification.setSslUri(SANDBOX_SPOT_URL);
          break;
        }
        case FUTURES: {
          if (!enabledSandbox(exchangeSpecification))
            exchangeSpecification.setSslUri(FUTURES_URL);
          else
            exchangeSpecification.setSslUri(SANDBOX_FUTURES_URL);
          break;
        }
        case INVERSE: {
          if (!enabledSandbox(exchangeSpecification))
            exchangeSpecification.setSslUri(INVERSE_FUTURES_URL);
          else
            exchangeSpecification.setSslUri(SANDBOX_INVERSE_FUTURES_URL);
          break;
        }
      }
    }
  }


  private static boolean enabledSandbox(ExchangeSpecification exchangeSpecification) {
    return Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
  }

}
