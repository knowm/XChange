package org.knowm.xchange.coinbasepro;

import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_PRIME_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_PRIME_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_PRIME_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_PRIME_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_USE_PRIME;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_USE_SANDBOX;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseProExchange extends BaseExchange {

  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      final boolean useSandbox =
          exchangeSpecification.getExchangeSpecificParametersItem(PARAM_USE_SANDBOX).equals(true);
      final boolean usePrime =
          Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(PARAM_USE_PRIME));
      if (useSandbox) {
        if (usePrime) {
          exchangeSpecification.setSslUri(
              (String)
                  exchangeSpecification.getExchangeSpecificParametersItem(
                      PARAM_SANDBOX_PRIME_SSL_URI));
          exchangeSpecification.setHost(
              (String)
                  exchangeSpecification.getExchangeSpecificParametersItem(
                      PARAM_SANDBOX_PRIME_HOST));
        } else {
          exchangeSpecification.setSslUri(
              (String)
                  exchangeSpecification.getExchangeSpecificParametersItem(PARAM_SANDBOX_SSL_URI));
          exchangeSpecification.setHost(
              (String) exchangeSpecification.getExchangeSpecificParametersItem(PARAM_SANDBOX_HOST));
        }
      } else if (usePrime) {
        exchangeSpecification.setSslUri(
            (String) exchangeSpecification.getExchangeSpecificParametersItem(PARAM_PRIME_SSL_URI));
        exchangeSpecification.setHost(
            (String) exchangeSpecification.getExchangeSpecificParametersItem(PARAM_PRIME_HOST));
      }
    }
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    this.marketDataService = new CoinbaseProMarketDataService(this, getResilienceRegistries());
    this.accountService = new CoinbaseProAccountService(this, getResilienceRegistries());
    this.tradeService = new CoinbaseProTradeService(this, getResilienceRegistries());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.pro.coinbase.com");
    exchangeSpecification.setHost("api.pro.coinbase.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CoinbasePro");
    exchangeSpecification.setExchangeDescription(
        "CoinbasePro Exchange is a Bitcoin exchange, re-branded from GDAX in 2018");

    exchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_SANDBOX, false);
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_SSL_URI, "https://api-public.sandbox.pro.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_HOST, "api-public.sandbox.pro.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_PRIME_SSL_URI, "https://api.exchange.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_PRIME_HOST, "api.exchange.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_SSL_URI, "https://api-public.sandbox.exchange.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_HOST, "api-public.sandbox.exchange.coinbase.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("CoinbasePro uses timestamp rather than a nonce");
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = CoinbaseProResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public void remoteInit() throws IOException {
    CoinbaseProProduct[] products =
        ((CoinbaseProMarketDataServiceRaw) marketDataService).getCoinbaseProProducts();
    CoinbaseProCurrency[] currencies =
        ((CoinbaseProMarketDataServiceRaw) marketDataService).getCoinbaseProCurrencies();
    exchangeMetaData =
        CoinbaseProAdapters.adaptToExchangeMetaData(exchangeMetaData, products, currencies);
  }

  // @NoArgsConstructor(access = AccessLevel.PRIVATE)
  // TODO: I don't know why this fails with `mvn install` yet
  public static final class Parameters {
    public static final String PARAM_USE_SANDBOX = "Use_Sandbox";
    public static final String PARAM_SANDBOX_SSL_URI = "SandboxSslUri";
    public static final String PARAM_SANDBOX_HOST = "SandboxHost";
    public static final String PARAM_USE_PRIME = "Use_Prime";
    public static final String PARAM_PRIME_SSL_URI = "PrimeSslUri";
    public static final String PARAM_PRIME_HOST = "PrimeHost";
    public static final String PARAM_SANDBOX_PRIME_SSL_URI = "SandboxPrimeSslUri";
    public static final String PARAM_SANDBOX_PRIME_HOST = "SandboxPrimeHost";
  }
}
