package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copied from BinanceLiveSubscriptionExample and replaced with BinanceFutureStreamingExchange from
 * BinanceStreamingExchange.
 */
public class BinanceLiveFutureSubscriptionExample {

  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceLiveFutureSubscriptionExample.class);

  public static void main(String[] args) throws InterruptedException {
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(BinanceFutureStreamingExchange.class)
            .getDefaultExchangeSpecification();
    BinanceFutureStreamingExchange exchange =
        (BinanceFutureStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    // First, we subscribe only for one instrument pair at connection time (minimum requirement)
    Instrument instrumentBTC = new FuturesContract("BTC/USDT/PERP");
    ProductSubscription subscription =
        ProductSubscription.create().addTrades(instrumentBTC).addOrderbook(instrumentBTC).build();
    // Note: at connection time, the live subscription is disabled
    exchange.connect(subscription).blockingAwait();

    // Note: See the doOnDispose below. It's here that we will send an unsubscribe request to
    // Binance through the websocket instance.
    Disposable tradesBtc =
        exchange
            .getStreamingMarketDataService()
            .getTrades(instrumentBTC)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(instrumentBTC, BinanceSubscriptionType.TRADE))
            .subscribe(trade -> LOG.info("Trade: {}", trade));

    Disposable orderBooksBtc =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(instrumentBTC)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(instrumentBTC, BinanceSubscriptionType.DEPTH))
            .subscribe(orderBook -> LOG.info("Order book: {}", orderBook.getAsks().get(0)));

    Thread.sleep(5000);

    // Now we enable the live subscription/unsubscription to add new currencies to the streams
    LOG.info("Enable live subscription/unsubscription");
    exchange.enableLiveSubscription();

    // We subscribe to 3 new currency pairs for trade (live subscription)
    // IMPORTANT!! Binance has a websocket limit of 5 incoming messages per second. If you bypass
    // this limit, the websocket will be disconnected.
    // (See
    // https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md#websocket-limits for more details)
    // If you plan to subscribe/unsubscribe more than 5 currency pairs at a time, use a rate limiter
    // or keep the live subscription
    // feature disabled and connect your pairs at connection time only (default value).
    final List<Instrument> currencyPairs =
        Arrays.asList(
            new FuturesContract("ETH/USDT/PERP"),
            new FuturesContract("LTC/USDT/PERP"),
            new FuturesContract("XRP/USDT/PERP"));
    final List<Disposable> disposableTrades = new ArrayList<>();
    for (final Instrument instrument : currencyPairs) {
      // Note: See the doOnDispose below. It's here that we will send an unsubscribe request to
      // Binance through the websocket instance.
      Disposable tradeDisposable =
          exchange
              .getStreamingMarketDataService()
              .getTrades(instrument)
              .doOnDispose(
                  () ->
                      exchange
                          .getStreamingMarketDataService()
                          .unsubscribe(instrument, BinanceSubscriptionType.TRADE))
              .subscribe(trade -> LOG.info("Trade: {}", trade));
      disposableTrades.add(tradeDisposable);
    }
    Thread.sleep(5000);

    // Now we unsubscribe BTC/USDT from the stream (TRADE and DEPTH) and also the another currency
    // pairs (TRADE 3x)
    // Note: we are ok with live unsubscription because we not bypass the limit of 5 messages per
    // second.
    tradesBtc.dispose();
    orderBooksBtc.dispose();
    disposableTrades.forEach(Disposable::dispose);

    LOG.info(
        "Now all symbols are live unsubscribed (BTC, ETH, LTC & XRP). We will live subscribe to XML/USDT and EOS/BTC...");
    Thread.sleep(5000);

    Disposable xlmDisposable =
        exchange
            .getStreamingMarketDataService()
            .getTrades(new FuturesContract("XLM/USDT/PERP"))
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(
                            new FuturesContract("XLM/USDT/PERP"), BinanceSubscriptionType.TRADE))
            .subscribe(trade -> {});
    Disposable eosDisposable =
        exchange
            .getStreamingMarketDataService()
            .getTrades(new FuturesContract("EOS/BTC/PERP"))
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(
                            new FuturesContract("EOS/BTC/PERP"), BinanceSubscriptionType.TRADE))
            .subscribe(trade -> {});

    Thread.sleep(5000);
    LOG.info("Test finished, we unsubscribe XML/USDT and EOS/BTC from the streams.");

    xlmDisposable.dispose();
    eosDisposable.dispose();

    exchange.disconnect().blockingAwait();
  }
}
