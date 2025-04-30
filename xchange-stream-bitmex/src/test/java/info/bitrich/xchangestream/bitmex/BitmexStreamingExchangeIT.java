package info.bitrich.xchangestream.bitmex;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeSpecification;

public class BitmexStreamingExchangeIT {

  public static StreamingExchange exchange;

  @BeforeAll
  public static void setup() {
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(BitmexStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setApiKey(System.getProperty("apiKey"));
    spec.setSecretKey(System.getProperty("secretKey"));
    spec.setPassword(System.getProperty("passphrase"));

    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect().blockingAwait();
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
