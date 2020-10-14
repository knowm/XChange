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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseProExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

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

    this.marketDataService = new CoinbaseProMarketDataService(this);
    this.accountService = new CoinbaseProAccountService(this);
    this.tradeService = new CoinbaseProTradeService(this);
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
        PARAM_PRIME_SSL_URI, "https://api.prime.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_PRIME_HOST, "api.prime.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_SSL_URI, "https://api-public.sandbox.prime.coinbase.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_HOST, "api-public.sandbox.prime.coinbase.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
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

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
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
