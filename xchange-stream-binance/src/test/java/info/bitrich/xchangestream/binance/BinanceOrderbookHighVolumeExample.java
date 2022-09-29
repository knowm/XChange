package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.functions.Consumer;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;

/**
 * This is a useful test for profiling behaviour of the orderbook stream under load. Run this with a
 * profiler to ensure that processing is efficient and free of memory leaks
 */
public class BinanceOrderbookHighVolumeExample {

  public static void main(String[] args) throws InterruptedException {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceStreamingExchange.class);
    exchangeSpecification.setShouldLoadRemoteMetaData(true);
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    ProductSubscription subscription =
        exchange.getExchangeSymbols().stream()
            .limit(50)
            .reduce(
                ProductSubscription.create(),
                ProductSubscription.ProductSubscriptionBuilder::addOrderbook,
                (productSubscriptionBuilder, productSubscriptionBuilder2) -> {
                  throw new UnsupportedOperationException();
                })
            .build();
    exchange.connect(subscription).blockingAwait();
    exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USDT).subscribe(new Consumer<OrderBook>() {
        @Override
        public void accept(OrderBook orderBook) throws Exception {
            System.out.println("##############\n");
            System.out.println(orderBook.toString());
            System.out.println("$$$$$$$$$$$$$$$\n");
        }
    });
    Thread.sleep(Long.MAX_VALUE);
  }
}
