package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_PRIME_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_PRIME_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_PRIME_HOST;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_PRIME_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_SANDBOX_SSL_URI;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_USE_PRIME;
import static org.knowm.xchange.coinbasepro.CoinbaseProExchange.Parameters.PARAM_USE_SANDBOX;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class CoinbaseProExchangeTest {

  @Test
  public void testCreateExchangeSandboxShouldApplyDefaultSandboxParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_SANDBOX, true);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://api-public.sandbox.pro.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("api-public.sandbox.pro.coinbase.com");
  }

  @Test
  public void testCreateExchangePrimeShouldApplyDefaultPrimeParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification primeExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    primeExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_PRIME, true);
    primeExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(primeExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://api.exchange.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("api.exchange.coinbase.com");
  }

  @Test
  public void testCreateExchangeSandboxPrimeShouldApplyDefaultSandboxPrimeParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification sandboxPrimeExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    sandboxPrimeExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_PRIME, true);
    sandboxPrimeExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_SANDBOX, true);
    sandboxPrimeExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(sandboxPrimeExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://api-public.sandbox.exchange.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("api-public.sandbox.exchange.coinbase.com");
  }

  @Test
  public void testCreateExchangeShouldApplyGivenSpecification() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification customExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    customExchangeSpecification.setSslUri("https://custom-server.pro.coinbase.com");
    customExchangeSpecification.setHost("custom-server.pro.coinbase.com");
    customExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(customExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://custom-server.pro.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("custom-server.pro.coinbase.com");
  }

  @Test
  public void testCreateExchangeSandboxShouldApplySandboxParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification customExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    customExchangeSpecification.setShouldLoadRemoteMetaData(false);
    customExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_SANDBOX, true);
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_SSL_URI, "https://custom-server.sandbox.pro.coinbase.com");
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_HOST, "custom-server.sandbox.pro.coinbase.com");

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(customExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://custom-server.sandbox.pro.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("custom-server.sandbox.pro.coinbase.com");
  }

  @Test
  public void testCreateExchangePrimeShouldApplyPrimeParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification customExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    customExchangeSpecification.setShouldLoadRemoteMetaData(false);
    customExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_PRIME, true);
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_PRIME_SSL_URI, "https://custom-server.prime.pro.coinbase.com");
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_PRIME_HOST, "custom-server.prime.pro.coinbase.com");

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(customExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://custom-server.prime.pro.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("custom-server.prime.pro.coinbase.com");
  }

  @Test
  public void testCreateExchangeSandboxPrimeShouldApplySandboxPrimeParams() {
    final CoinbaseProExchange coinbaseProExchange = new CoinbaseProExchange();
    final ExchangeSpecification customExchangeSpecification =
        coinbaseProExchange.getDefaultExchangeSpecification();
    customExchangeSpecification.setShouldLoadRemoteMetaData(false);
    customExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_PRIME, true);
    customExchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_SANDBOX, true);
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_SSL_URI, "https://custom-server.sandbox.prime.pro.coinbase.com");
    customExchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_SANDBOX_PRIME_HOST, "custom-server.sandbox.prime.pro.coinbase.com");

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(customExchangeSpecification);

    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo("https://custom-server.sandbox.prime.pro.coinbase.com");
    assertThat(exchange.getExchangeSpecification().getHost())
        .isEqualTo("custom-server.sandbox.prime.pro.coinbase.com");
  }
}
