package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

@Ignore
public class BybitStreamingPrivateEndpointsTest {

  Instrument instrument = new FuturesContract("BTC/USDT/PERP");
  StreamingExchange exchange;
  @Before
  public void setUp() {
    Properties properties = new Properties();

    try {
      properties.load(this.getClass().getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new BybitExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("apikey"));
    spec.setSecretKey(properties.getProperty("secret"));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);

    exchange = StreamingExchangeFactory.INSTANCE.createExchange(BybitStreamingExchange.class);
    exchange.applySpecification(spec);

    exchange.connect(ProductSubscription.create().build()).blockingAwait();
  }

  @Test
  public void testUserTrades() throws InterruptedException {

    exchange.getStreamingTradeService().getUserTrades(instrument).subscribe(System.out::println);

    TimeUnit.SECONDS.sleep(5);
  }
}
