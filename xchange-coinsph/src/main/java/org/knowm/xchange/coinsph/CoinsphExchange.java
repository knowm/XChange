package org.knowm.xchange.coinsph;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.dto.CoinsphJacksonObjectMapperFactory;
import org.knowm.xchange.coinsph.dto.meta.CoinsphExchangeInfo;
import org.knowm.xchange.coinsph.service.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphExchange extends BaseExchange implements Exchange {
  private static final Logger LOG = LoggerFactory.getLogger(CoinsphExchange.class);

  // Coins.ph specific URLs
  public static final String PARAM_RECV_WINDOW = "recvWindow";
  private static final String PRODUCTION_URL =
      "https://api.pro.coins.ph"; // Placeholder, verify actual URL
  public static final String SANDBOX_URL = "https://9001.pl-qa.coinsxyz.me";

  protected static ResilienceRegistries RESILIENCE_REGISTRIES;
  protected SynchronizedValueFactory<Long> timestampFactory;
  protected Coinsph publicApi;
  protected CoinsphAuthenticated authenticatedApi;
  protected ParamsDigest signatureCreator; // CoinsphDigest should implement/extend ParamsDigest

  @Override
  protected void initServices() {
    this.timestampFactory =
        CoinsphTimestampFactory.createFactory(
            getPublicApi(), getExchangeSpecification(), getResilienceRegistries());
    this.marketDataService = new CoinsphMarketDataService(this, getResilienceRegistries());
    this.tradeService = new CoinsphTradeService(this, getResilienceRegistries());
    this.accountService = new CoinsphAccountService(this, getResilienceRegistries());
  }

  public SynchronizedValueFactory<Long> getTimestampFactory() {
    return timestampFactory;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // Coins.ph uses a timestamp for signed requests, similar to Binance.
    // The timestampFactory provides this synchronized time.
    return timestampFactory;
  }

  public Coinsph getPublicApi() {
    return publicApi;
  }

  public CoinsphAuthenticated getAuthenticatedApi() {
    return authenticatedApi;
  }

  public ParamsDigest getSignatureCreator() {
    return signatureCreator;
  }

  public static void resetResilienceRegistries() {
    RESILIENCE_REGISTRIES = null;
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = CoinsphResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri(PRODUCTION_URL); // Default to production
    spec.setHost("api.coins.ph"); // Placeholder, verify actual host
    spec.setPort(443); // Default HTTPS port
    spec.setExchangeName("Coins.ph");
    spec.setExchangeDescription("Coins.ph Exchange.");
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    AuthUtils.setApiAndSecretKey(spec, "coinsph"); // For storing API key/secret in properties file
    return spec;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    concludeHostParams(exchangeSpecification); // Set correct URL based on sandbox mode

    // Initialize API proxies using the provided exchangeSpecification
    // BEFORE calling super.applySpecification() which might call remoteInit/initServices
    Interceptor errorInterceptor = new CoinsphErrorInterceptor();
    this.publicApi =
        ExchangeRestProxyBuilder.forInterface(Coinsph.class, exchangeSpecification)
            .customInterceptor(errorInterceptor)
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new CoinsphJacksonObjectMapperFactory()))
            .build();
    this.authenticatedApi =
        ExchangeRestProxyBuilder.forInterface(CoinsphAuthenticated.class, exchangeSpecification)
            .customInterceptor(errorInterceptor)
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new CoinsphJacksonObjectMapperFactory()))
            .build();

    // Initialize signature creator
    // Use the passed exchangeSpecification as this.exchangeSpecification might not be set yet by
    // super
    if (exchangeSpecification.getSecretKey() != null) {
      this.signatureCreator =
          CoinsPHSignatureCreator.createInstance(exchangeSpecification.getSecretKey());
    } else {
      LOG.warn("Secret key not provided. Authenticated services will not be available.");
    }

    super.applySpecification(
        exchangeSpecification); // Now call super, which will set this.exchangeSpecification and
    // call initServices/remoteInit
  }

  public boolean usingSandbox() {
    return Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(Exchange.USE_SANDBOX));
  }

  public Long getRecvWindow() {
    // Coins.ph docs: "recvWindow (optional)"
    // "If recvWindow is not sent with the request, it will default to 5000." - from Binance docs,
    // assuming Coins.ph is similar.
    // So, if null, the server will use its default. We can return null here.
    Object recvWindowObj =
        exchangeSpecification.getExchangeSpecificParametersItem(PARAM_RECV_WINDOW);
    if (recvWindowObj == null) {
      return null;
    }
    if (recvWindowObj instanceof Number) {
      return ((Number) recvWindowObj).longValue();
    }
    try {
      return Long.parseLong(recvWindowObj.toString());
    } catch (NumberFormatException e) {
      LOG.warn(
          "Invalid format for {} parameter: {}. Using null.", PARAM_RECV_WINDOW, recvWindowObj, e);
      return null;
    }
  }

  @Override
  public void remoteInit() {
    try {
      LOG.debug("Starting remoteInit for Coins.ph");
      // Fetch exchange info
      CoinsphMarketDataServiceRaw marketDataServiceRaw =
          (CoinsphMarketDataServiceRaw) this.marketDataService;
      CoinsphExchangeInfo exchangeInfo =
          this.publicApi.exchangeInfo(); // Use direct publicApi field
      LOG.debug("Fetched CoinsphExchangeInfo: {}", exchangeInfo);

      // Adapt to XChange DTOs
      // Asset details are not part of Coins.ph exchangeInfo, so pass null
      exchangeMetaData = CoinsphAdapters.adaptExchangeMetaData(exchangeInfo);
      LOG.debug("Adapted ExchangeMetaData: {}", exchangeMetaData);

      // Symbol mapping is handled within CoinsphAdapters.toCurrencyPair and adaptExchangeMetaData
      // No explicit putSymbolMapping needed here if CoinsphAdapters is robust.

      // Resync timestamp factory after potentially long call
      if (timestampFactory instanceof CoinsphTimestampFactory) {
        ((CoinsphTimestampFactory) timestampFactory).resync();
      }
      LOG.info("Coins.ph remoteInit finished successfully.");

    } catch (Exception e) {
      // SynchronizedValueFactory should not throw an exception, so we can catch them here.
      if (timestampFactory instanceof CoinsphTimestampFactory) {
        ((CoinsphTimestampFactory) timestampFactory)
            .resync(); // Try to resync time even if remoteInit failed
      }
      throw new ExchangeException("Failed to initialize Coins.ph exchange: " + e.getMessage(), e);
    }
  }

  protected boolean isAuthenticated() {
    return exchangeSpecification != null
        && exchangeSpecification.getApiKey() != null
        && exchangeSpecification.getSecretKey() != null;
  }

  /** Adjust host parameters depending on exchange specific parameters */
  protected void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
      exchangeSpecification.setSslUri(SANDBOX_URL);
      // Update host if necessary for sandbox
      // spec.setHost("9001.pl-qa.coinsxyz.me"); // Example, if different from SslUri's host
    } else {
      exchangeSpecification.setSslUri(PRODUCTION_URL);
      // Update host if necessary for production
      // spec.setHost("api.coins.ph"); // Example
    }
  }
}
