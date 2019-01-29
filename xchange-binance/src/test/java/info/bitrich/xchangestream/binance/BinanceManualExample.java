package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;

import io.reactivex.disposables.Disposable;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class BinanceManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceManualExample.class);

    public static void main(String[] args) throws InterruptedException {
        // Far safer than temporarily adding these to code that might get committed to VCS
        String apiKey = System.getProperty("binance-api-key");
        String apiSecret = System.getProperty("binance-api-secret");

        ExchangeSpecification spec = StreamingExchangeFactory.INSTANCE.createExchange(
            BinanceStreamingExchange.class.getName()).getDefaultExchangeSpecification();
        spec.setApiKey(apiKey);
        spec.setSecretKey(apiSecret);
        BinanceStreamingExchange exchange = (BinanceStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

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

        Disposable orderChanges = null;
        Disposable userTrades = null;

        if (apiKey != null) {
            orderChanges = exchange.getStreamingMarketDataService()
                .getOrderChanges()
                .subscribe(oc -> {
                    LOG.info("Order change: {}", oc);
                });
            userTrades = exchange.getStreamingMarketDataService()
                .getUserTrades()
                .subscribe(trade -> {
                    LOG.info("User trade: {}", trade);
                });
        }

        Disposable orderbooks = orderbooks(exchange, "one");
        Thread.sleep(5000);

        Disposable orderbooks2 = orderbooks(exchange, "two");
        Thread.sleep(10000);

        tickers.dispose();
        trades.dispose();
        orderbooks.dispose();
        orderbooks2.dispose();

        if (apiKey != null) {
            orderChanges.dispose();
            userTrades.dispose();
        }

        Thread.sleep(1000000);

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
