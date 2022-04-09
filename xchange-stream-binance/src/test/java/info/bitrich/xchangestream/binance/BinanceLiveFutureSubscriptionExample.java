package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // First, we subscribe only for one currency pair at connection time (minimum requirement)
    ProductSubscription subscription =
        ProductSubscription.create()
            .addTrades(CurrencyPair.BTC_USDT)
            .addOrderbook(CurrencyPair.BTC_USDT)
            .build();
    // Note: at connection time, the live subscription is disabled
    exchange.connect(subscription).blockingAwait();

    // Note: See the doOnDispose below. It's here that we will send an unsubscribe request to
    // Binance through the websocket instance.
    Disposable tradesBtc =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USDT)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(CurrencyPair.BTC_USDT, BinanceSubscriptionType.TRADE))
            .subscribe(
                trade -> {
                  LOG.info("Trade: {}", trade);
                });

    Disposable orderBooksBtc =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USDT)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(CurrencyPair.BTC_USDT, BinanceSubscriptionType.DEPTH))
            .subscribe(
                orderBook -> {
                  LOG.info("Order book: {}", orderBook);
                });

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
    final List<CurrencyPair> currencyPairs =
        Arrays.asList(CurrencyPair.ETH_USDT, CurrencyPair.LTC_USDT, CurrencyPair.XRP_USDT);
    final List<Disposable> disposableTrades = new ArrayList<>();
    for (final CurrencyPair currencyPair : currencyPairs) {
      // Note: See the doOnDispose below. It's here that we will send an unsubscribe request to
      // Binance through the websocket instance.
      Disposable tradeDisposable =
          exchange
              .getStreamingMarketDataService()
              .getTrades(currencyPair)
              .doOnDispose(
                  () ->
                      exchange
                          .getStreamingMarketDataService()
                          .unsubscribe(currencyPair, BinanceSubscriptionType.TRADE))
              .subscribe(
                  trade -> {
                    LOG.info("Trade: {}", trade);
                  });
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
            .getTrades(CurrencyPair.XLM_USDT)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(CurrencyPair.XLM_USDT, BinanceSubscriptionType.TRADE))
            .subscribe(trade -> {});
    Disposable eosDisposable =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.EOS_BTC)
            .doOnDispose(
                () ->
                    exchange
                        .getStreamingMarketDataService()
                        .unsubscribe(CurrencyPair.EOS_BTC, BinanceSubscriptionType.TRADE))
            .subscribe(trade -> {});

    Thread.sleep(5000);
    LOG.info("Test finished, we unsubscribe XML/USDT and EOS/BTC from the streams.");

    xlmDisposable.dispose();
    eosDisposable.dispose();

    exchange.disconnect().blockingAwait();
  }
}
