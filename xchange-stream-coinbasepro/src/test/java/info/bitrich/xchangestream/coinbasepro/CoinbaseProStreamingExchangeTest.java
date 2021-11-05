package info.bitrich.xchangestream.coinbasepro;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;

public class CoinbaseProStreamingExchangeTest {

  @Test
  public void testOverrideWebsocketApiUriWhenUsingSandBoxAndPrime() {
    final String overrideWebsocketApiUri = "wss://demo.websocket.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, true);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, true);
    sandboxExchangeSpecification.setOverrideWebsocketApiUri(overrideWebsocketApiUri);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(overrideWebsocketApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri())
        .isEqualTo(overrideWebsocketApiUri);
  }

  @Test
  public void testOverrideWebsocketApiUriWhenUsingSandBox() {
    final String overrideWebsocketApiUri = "wss://demo.websocket.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, true);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, false);
    sandboxExchangeSpecification.setOverrideWebsocketApiUri(overrideWebsocketApiUri);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(overrideWebsocketApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri())
        .isEqualTo(overrideWebsocketApiUri);
  }

  @Test
  public void testOverrideWebsocketApiUriWhenUsingPrime() {
    final String overrideWebsocketApiUri = "wss://demo.websocket.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, false);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, true);
    sandboxExchangeSpecification.setOverrideWebsocketApiUri(overrideWebsocketApiUri);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(overrideWebsocketApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri())
        .isEqualTo(overrideWebsocketApiUri);
  }

  @Test
  public void testOverrideWebsocketApiUri() {
    final String overrideWebsocketApiUri = "wss://demo.websocket.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, false);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, false);
    sandboxExchangeSpecification.setOverrideWebsocketApiUri(overrideWebsocketApiUri);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(overrideWebsocketApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri())
        .isEqualTo(overrideWebsocketApiUri);
  }

  @Test
  public void testWhenUsingSandbox() {
    final String sandboxApiUri = "wss://ws-feed-public.sandbox.pro.coinbase.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, true);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, false);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(sandboxApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri()).isEqualTo(null);
  }

  @Test
  public void testWhenUsingSandboxWithPrime() {
    final String primeSandboxApiUri = "wss://ws-feed-public.sandbox.exchange.coinbase.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, true);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, true);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(primeSandboxApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri()).isEqualTo(null);
  }

  @Test
  public void testWhenUsingPrime() {
    final String primeApiUri = "wss://ws-feed.exchange.coinbase.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, false);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, true);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(primeApiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri()).isEqualTo(null);
  }

  @Test
  public void testWhenUsingOnlyApiUri() {
    final String apiUri = "wss://ws-feed.pro.coinbase.com";
    final CoinbaseProStreamingExchange coinbaseProStreamingExchange =
        new CoinbaseProStreamingExchange();
    final ExchangeSpecification sandboxExchangeSpecification =
        coinbaseProStreamingExchange.getDefaultExchangeSpecification();
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_SANDBOX, false);
    sandboxExchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_USE_PRIME, false);
    sandboxExchangeSpecification.setShouldLoadRemoteMetaData(false);

    final CoinbaseProStreamingExchange exchange =
        (CoinbaseProStreamingExchange)
            StreamingExchangeFactory.INSTANCE.createExchange(sandboxExchangeSpecification);

    assertThat(exchange.getApiUri()).isEqualTo(apiUri);
    assertThat(exchange.getExchangeSpecification().getOverrideWebsocketApiUri()).isEqualTo(null);
  }

  public static final class Parameters {
    public static final String PARAM_USE_SANDBOX = "Use_Sandbox";
    public static final String PARAM_USE_PRIME = "Use_Prime";
  }
}
