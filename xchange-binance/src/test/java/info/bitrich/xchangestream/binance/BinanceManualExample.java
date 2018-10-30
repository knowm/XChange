package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.Disposable;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class BinanceManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceManualExample.class);

    public static void main(String[] args) throws InterruptedException {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class.getName());

        ProductSubscription subscription = ProductSubscription.create()
                .addTicker(CurrencyPair.ETH_BTC)
                .addTicker(CurrencyPair.LTC_BTC)
                .addOrderbook(CurrencyPair.LTC_BTC)
                .addTrades(CurrencyPair.BTC_USDT)
                .build();

        exchange.connect(subscription).blockingAwait();

        Disposable tickers = exchange.getStreamingMarketDataService()
                .getTicker(CurrencyPair.ETH_BTC)
                .subscribe(ticker -> {
                    LOG.info("Ticker: {}", ticker);
                }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        Disposable trades = exchange.getStreamingMarketDataService()
                .getTrades(CurrencyPair.BTC_USDT)
                .subscribe(trade -> {
                    LOG.info("Trade: {}", trade);
                });

        Disposable orderbooks = orderbooks(exchange, "one");
        Thread.sleep(5000);

        Disposable orderbooks2 = orderbooks(exchange, "two");
        Thread.sleep(10000);

        tickers.dispose();
        trades.dispose();
        orderbooks.dispose();
        orderbooks2.dispose();
        exchange.disconnect().blockingAwait();

    }

    private static Disposable orderbooks(StreamingExchange exchange, String identifier) {
      return exchange.getStreamingMarketDataService()
              .getOrderBook(CurrencyPair.LTC_BTC)
              .subscribe(orderBook -> {
                  LOG.info(
                      "Order Book ({}): askDepth={} ask={} askSize={} bidDepth={}. bid={}, bidSize={}",
                      identifier,
                      orderBook.getAsks().size(),
                      orderBook.getAsks().get(0).getLimitPrice(),
                      orderBook.getAsks().get(0).getRemainingAmount(),
                      orderBook.getBids().size(),
                      orderBook.getBids().get(0).getLimitPrice(),
                      orderBook.getBids().get(0).getRemainingAmount()
                  );
              }, throwable -> LOG.error("ERROR in getting order book: ", throwable));
    }
}
