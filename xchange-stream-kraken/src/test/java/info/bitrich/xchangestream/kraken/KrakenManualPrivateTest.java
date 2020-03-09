package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenManualPrivateTest {
  private static final Logger LOG = LoggerFactory.getLogger(KrakenManualExample.class);

  public static void main(String[] args) throws InterruptedException {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);
    exchangeSpecification.setApiKey(args[0]);
    exchangeSpecification.setSecretKey(args[1]);
    exchangeSpecification.setUserName(args[2]);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    krakenExchange
        .getStreamingTradeService()
        .getUserTrades(null)
        .subscribe(
            b -> {
              LOG.info("Received userTrade {}", b);
            },
            throwable -> {
              LOG.error("UserTrades FAILED {}", throwable.getMessage(), throwable);
            });
    krakenExchange
        .getStreamingTradeService()
        .getOrderChanges(null)
        .subscribe(
            b -> {
              LOG.info("Received orderChange {}", b);
            },
            throwable -> {
              LOG.error("OrderChange FAILED {}", throwable.getMessage(), throwable);
            });

    TimeUnit.SECONDS.sleep(120);

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
