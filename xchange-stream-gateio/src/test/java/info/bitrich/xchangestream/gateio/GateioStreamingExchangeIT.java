package info.bitrich.xchangestream.gateio;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeSpecification;

public class GateioStreamingExchangeIT {

  public static GateioStreamingExchange exchange;

  @BeforeAll
  public static void setup() {
    try {

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setApiKey(System.getenv("GATEIO_API_KEY"));
    spec.setSecretKey(System.getenv("GATEIO_API_SECRET"));

     exchange =
        (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect().blockingAwait();
    }
    catch (Exception ignored) {

    }
  }


  @BeforeEach
  void exchangeReachable() {
    assumeTrue(exchange.isAlive(), "Exchange is unreachable");
  }


  @AfterAll
  public static void cleanup() {
    if (exchange.isAlive()) {
      exchange.disconnect().blockingAwait();
    }
  }



}