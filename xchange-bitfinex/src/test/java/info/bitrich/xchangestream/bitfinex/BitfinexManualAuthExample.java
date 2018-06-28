package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitfinexManualAuthExample {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexManualAuthExample.class);

    public static void main(String[] args) {
        BitfinexStreamingExchange exchange = (BitfinexStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(
            BitfinexStreamingExchange.class.getName());
        exchange.setCredentials("API-KEY", "API-SECRET");

        exchange.connectToAuthenticated().blockingAwait();
        exchange.getStreamingAuthenticatedDataService().getAuthenticatedTrades().subscribe(
            t -> LOG.info("AUTH TRADE: {}", t),
            throwable -> LOG.error("ERROR: ", throwable)
        );
        exchange.getStreamingAuthenticatedDataService().getAuthenticatedPreTrades().subscribe(
            t -> LOG.info("AUTH PRE TRADE: {}", t),
            throwable -> LOG.error("ERROR: ", throwable)
        );
        exchange.getStreamingAuthenticatedDataService().getAuthenticatedOrders().subscribe(
            t -> LOG.info("AUTH ORDER: {}", t),
            throwable -> LOG.error("ERROR: ", throwable)
        );
        exchange.getStreamingAuthenticatedDataService().getAuthenticatedBalances().subscribe(
            t -> LOG.info("AUTH BALANCE: {}", t),
            throwable -> LOG.error("ERROR: ", throwable)
        );
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
