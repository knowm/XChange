
## Binance

### Live Subscription/Unsubscription
The feature to support Live Subscribe/Unsubcribe to streams has been added on 2021-02-24.
This allow to subscribe new currency pairs without disconnecting the streams.

To use this feature, follow these steps:
```java
ExchangeSpecification spec = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class)
    .getDefaultExchangeSpecification();
BinanceStreamingExchange exchange = (BinanceStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

// First, we need to subscribe to at least one currency pair at connection time
// Note: at connection time, the live subscription is disabled
ProductSubscription subscription =
    ProductSubscription.create().addTrades(CurrencyPair.BTC_USDT).addOrderbook(CurrencyPair.BTC_USDT).build();
exchange.connect(subscription).blockingAwait();

// We subscribe to trades update for the currency pair subscribed at connection time (BTC)
// For live unsubscription, you need to add a doOnDispose that will call the method unsubscribe in BinanceStreamingMarketDataService
Disposable tradesBtc = exchange.getStreamingMarketDataService()
    .getTrades(CurrencyPair.BTC_USDT)
    .doOnDispose(
        () -> exchange.getStreamingMarketDataService().unsubscribe(CurrencyPair.BTC_USDT, BinanceSubscriptionType.TRADE))
    .subscribe(trade -> { LOG.info("Trade: {}", trade); });

// Now we enable the live subscription/unsubscription to add new currencies to the streams
exchange.enableLiveSubscription();

// We live subscribe a new currency pair to the trades update
Disposable tradesEth = exchange.getStreamingMarketDataService()
    .getTrades(CurrencyPair.ETH_USDT)
    .doOnDispose(
        () -> exchange.getStreamingMarketDataService().unsubscribe(CurrencyPair.ETH_USDT, BinanceSubscriptionType.TRADE))
    .subscribe(trade -> { LOG.info("Trade: {}", trade); });

Thread.sleep(30000);

// We unsubscribe from the streams
tradesBtc.dispose();
tradesEth.dispose();
```

### IMPORTANT NOTE
When using Live Subscription/Unsubscription, Binance has a websocket limit of 5 incoming messages per second. If you bypass this limit, the websocket will be disconnected.
See https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md#websocket-limits for more details.

If you plan to subscribe/unsubscribe more than 5 currency pairs at a time, use a rate limiter or keep the live subscription feature disabled and connect your pairs at connection time only (default value).


 