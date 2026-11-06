package info.bitrich.xchangestream.gateio;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static info.bitrich.xchangestream.core.StreamingExchange.*;
import static info.bitrich.xchangestream.service.netty.NettyStreamingService.DEFAULT_IDLE_TIMEOUT;
import static info.bitrich.xchangestream.service.netty.NettyStreamingService.DEFAULT_RETRY_DURATION;
import static org.assertj.core.api.Assertions.assertThat;

class GateioUserTradeStreamingServiceTest {

  private ExchangeSpecification exchangeSpecification;

  static class TestableGateioUserTradeStreamingService extends GateioUserTradeStreamingService {
    public TestableGateioUserTradeStreamingService(String privateApiUrl, String apiSecret,
                                                   ExchangeSpecification exchangeSpecification, boolean isFuturesEnabled) {
      super(privateApiUrl, apiSecret, exchangeSpecification, isFuturesEnabled);
    }

    @Override
    protected Completable openConnection() {
      return Completable.complete();
    }

    @Override
    public void sendMessage(String message) {
      // no-op for unit testing without netty socket
    }
  }

  @BeforeEach
  void setUp() {
    exchangeSpecification = new ExchangeSpecification(GateioStreamingExchange.class);
    exchangeSpecification.setApiKey("test-api-key");
    exchangeSpecification.setSecretKey("test-api-secret");
    exchangeSpecification.setExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT, Duration.ofMillis(200));
    exchangeSpecification.setExchangeSpecificParametersItem(WS_RETRY_DURATION, DEFAULT_RETRY_DURATION);
    exchangeSpecification.setExchangeSpecificParametersItem(WS_IDLE_TIMEOUT, DEFAULT_IDLE_TIMEOUT);
  }

  @Test
  void connect_completes_when_login_ack_arrives_spot() {
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://api.gateio.ws/ws/v4/", "test-api-secret", exchangeSpecification, false);

    TestObserver<Void> testObserver = service.connect().test();

    testObserver.assertNotComplete();
    assertThat(service.isLoginDone()).isFalse();

    service.messageHandler("{\"header\":{\"channel\":\"spot.login\",\"status\":\"200\"}}");

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    assertThat(service.isLoginDone()).isTrue();
  }

  @Test
  void connect_completes_when_login_ack_arrives_futures() {
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://fx-ws.gateio.ws/v4/ws/usdt", "test-api-secret", exchangeSpecification, true);

    TestObserver<Void> testObserver = service.connect().test();

    testObserver.assertNotComplete();
    assertThat(service.isLoginDone()).isFalse();

    service.messageHandler("{\"header\":{\"channel\":\"futures.login\",\"status\":\"200\"}}");

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    assertThat(service.isLoginDone()).isTrue();
  }

  @Test
  void connect_errors_when_login_returns_non_200() {
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://api.gateio.ws/ws/v4/", "test-api-secret", exchangeSpecification, false);

    TestObserver<Void> testObserver = service.connect().test();

    testObserver.assertNotComplete();
    assertThat(service.isLoginDone()).isFalse();

    service.messageHandler("{\"header\":{\"channel\":\"spot.login\",\"status\":\"401\"},"
        + "\"data\":{\"errs\":{\"label\":\"INVALID_KEY\",\"message\":\"Invalid API key\"}}}");

    testObserver.assertError(e -> e instanceof ExchangeSecurityException
        && e.getMessage().equals("Login failed: status 401 - INVALID_KEY - Invalid API key"));
    assertThat(service.isLoginDone()).isFalse();
  }

  @Test
  void connect_errors_on_timeout_if_ack_never_arrives() {
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://api.gateio.ws/ws/v4/", "test-api-secret", exchangeSpecification, false);

    TestObserver<Void> testObserver = service.connect().test();

    testObserver.awaitDone(1, TimeUnit.SECONDS);

    testObserver.assertError(TimeoutException.class);
    assertThat(service.isLoginDone()).isFalse();
  }

  @Test
  void connect_errors_when_api_key_missing() {
    exchangeSpecification.setApiKey(null);
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://api.gateio.ws/ws/v4/", "test-api-secret", exchangeSpecification, false);

    TestObserver<Void> testObserver = service.connect().test();

    testObserver.assertError(e -> e instanceof ExchangeSecurityException
        && e.getMessage().equals("API key is not specified"));
    assertThat(service.isLoginDone()).isFalse();
  }

  @Test
  void disconnect_resets_login_done() {
    TestableGateioUserTradeStreamingService service =
        new TestableGateioUserTradeStreamingService("wss://api.gateio.ws/ws/v4/", "test-api-secret", exchangeSpecification, false);

    TestObserver<Void> testObserver = service.connect().test();
    service.messageHandler("{\"header\":{\"channel\":\"spot.login\",\"status\":\"200\"}}");
    testObserver.assertComplete();
    assertThat(service.isLoginDone()).isTrue();

    service.disconnect().test().assertComplete();
    assertThat(service.isLoginDone()).isFalse();
  }
}
