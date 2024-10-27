package info.bitrich.xchangestream.bitget;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BitgetStreamingExchangeIT {

  public static StreamingExchange exchange;

  @BeforeAll
  public static void setup() {
      exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitgetStreamingExchange.class);
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
