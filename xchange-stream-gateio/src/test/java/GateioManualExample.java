import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateioManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(GateioManualExample.class);

    public static void main(String[] args) throws Exception {
        ProductSubscription productSubscription =
                ProductSubscription.create()
                        .addOrderbook(CurrencyPair.ETH_USDT)
                        .addOrderbook(CurrencyPair.BTC_USDT)
                        .addTrades(CurrencyPair.ETH_USDT)
                        .build();

        ExchangeSpecification spec =
                StreamingExchangeFactory.INSTANCE
                        .createExchange(GateioStreamingExchange.class)
                        .getDefaultExchangeSpecification();

        GateioStreamingExchange exchange =
                (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

        exchange.connect(productSubscription).blockingAwait();

        exchange
                .getStreamingMarketDataService()
                .getOrderBook(CurrencyPair.ETH_USDT)
                .subscribe(
                        orderBook -> {
                            LOG.info("First ask: {}", orderBook.getAsks().get(0));
                            LOG.info("First bid: {}", orderBook.getBids().get(0));
                        },
                        throwable -> LOG.error("ERROR in getting order book: ", throwable));
        exchange
                .getStreamingMarketDataService()
                .getOrderBook(CurrencyPair.BTC_USDT)
                .subscribe(
                        orderBook -> {
                            LOG.info("First ask: {}", orderBook.getAsks().get(0));
                            LOG.info("First bid: {}", orderBook.getBids().get(0));
                        },
                        throwable -> LOG.error("ERROR in getting order book: ", throwable));
        exchange
                .getStreamingMarketDataService()
                .getTrades(CurrencyPair.BTC_USDT)
                .subscribe(
                        trade -> {
                            LOG.info("Trade Price: {}", trade.getPrice());
                            LOG.info("Trade Amount: {}", trade.getOriginalAmount());
                        },
                        throwable -> LOG.error("ERROR in getting trade: ", throwable));
    }
}
