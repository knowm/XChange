package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitfinexManualAuthExample {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexManualAuthExample.class);

    public static void main(String[] args) {

        // Far safer than temporarily adding these to code that might get committed to VCS
        String apiKey = System.getProperty("bitfinex-api-key");
        String apiSecret = System.getProperty("bitfinex-api-secret");
        if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(apiSecret)) {
            throw new IllegalArgumentException("Supply api details in VM args");
        }

        ExchangeSpecification spec = StreamingExchangeFactory.INSTANCE.createExchange(
            BitfinexStreamingExchange.class.getName()).getDefaultExchangeSpecification();
        spec.setApiKey(apiKey);
        spec.setSecretKey(apiSecret);
        BitfinexStreamingExchange exchange = (BitfinexStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

        exchange.connect().blockingAwait();
        try {
            exchange.getStreamingMarketDataService().getRawAuthenticatedTrades().subscribe(
                t -> LOG.info("AUTH TRADE: {}", t),
                throwable -> LOG.error("ERROR: ", throwable)
            );
            exchange.getStreamingMarketDataService().getRawAuthenticatedPreTrades().subscribe(
                t -> LOG.info("AUTH PRE TRADE: {}", t),
                throwable -> LOG.error("ERROR: ", throwable)
            );
            exchange.getStreamingMarketDataService().getRawAuthenticatedOrders().subscribe(
                t -> LOG.info("AUTH ORDER: {}", t),
                throwable -> LOG.error("ERROR: ", throwable)
            );
            exchange.getStreamingMarketDataService().getRawAuthenticatedBalances().subscribe(
                t -> LOG.info("AUTH BALANCE: {}", t),
                throwable -> LOG.error("ERROR: ", throwable)
            );

            // Make sure we can still get unauthenticated data on the same socket
            exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(
                t -> LOG.info("PUBLIC TRADE: {}", t),
                throwable -> LOG.error("ERROR: ", throwable)
            );
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            exchange.disconnect().blockingAwait();
        }
    }
}
