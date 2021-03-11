package info.bitrich.xchangestream.dydx;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.knowm.xchange.dydx.dydxExchange.V3;

public class dydxManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(dydxManualExample.class);

    public static void main(String[] args) {
        ProductSubscription productSubscription =
                ProductSubscription.create()
                        .addOrderbook(CurrencyPair.ETH_USD)
                        .addOrderbook(CurrencyPair.BTC_USD)
                        .addOrderbook(CurrencyPair.LINK_USD)
                        .build();

        ExchangeSpecification spec =
                StreamingExchangeFactory.INSTANCE
                        .createExchange(dydxStreamingExchange.class)
                        .getDefaultExchangeSpecification();
        spec.setExchangeSpecificParametersItem("version", V3);

        dydxStreamingExchange exchange =
                (dydxStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

        exchange.connect(productSubscription).blockingAwait();

        exchange
                .getStreamingMarketDataService()
                .getOrderBook(CurrencyPair.ETH_USD)
                .subscribe(
                        orderBook -> {
                            LOG.info("First ask: {}", orderBook.getAsks().get(0));
                            LOG.info("First bid: {}", orderBook.getBids().get(0));
                        },
                        throwable -> LOG.error("ERROR in getting order book: ", throwable));
    }
}
