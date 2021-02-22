package info.bitrich.xchangestream.dydx;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class dydxManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(dydxManualExample.class);

    public static void main(String[] args) {
        ProductSubscription productSubscription =
                ProductSubscription.create()
                        .addOrderbook(CurrencyPair.WETH_USDC)
                        .addOrderbook(CurrencyPair.WETH_DAI)
                        .addOrderbook(CurrencyPair.WETH_PUSD)
                        .build();

        ExchangeSpecification spec =
                StreamingExchangeFactory.INSTANCE
                        .createExchange(dydxStreamingExchange.class)
                        .getDefaultExchangeSpecification();

        spec.setExchangeSpecificParametersItem("batch_messages", false);
        dydxStreamingExchange exchange =
                (dydxStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

        exchange.connect(productSubscription).blockingAwait();

        exchange
                .getStreamingMarketDataService()
                .getOrderBook(CurrencyPair.WETH_USDC)
                .subscribe(
                        orderBook -> {
                            LOG.info("First ask: {}", orderBook.getAsks().get(0));
                            LOG.info("First bid: {}", orderBook.getBids().get(0));
                        },
                        throwable -> LOG.error("ERROR in getting order book: ", throwable));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exchange.disconnect().blockingAwait();
    }
}
