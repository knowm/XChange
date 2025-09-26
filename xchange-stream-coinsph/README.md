# Coins.ph Streaming Exchange

This module implements the XChange Streaming API for the [Coins.ph Exchange](https://coins.ph/).

## Info

* WebSocket API Documentation: 
  * WebSocket API: https://docs.coins.ph/web-socket-streams/
  * User Data Stream: https://docs.coins.ph/user-data-stream/

## Features

This implementation supports the following WebSocket streams:

### Market Data
* Order book updates
* Ticker updates
* Trade updates

### Trading
* User order updates
* User trade updates

### Account
* Balance updates

## Usage

### Basic Connection

```java
// Create Streaming Exchange
ExchangeSpecification spec = new ExchangeSpecification(CoinsphStreamingExchange.class);
spec.setApiKey("your-api-key");
spec.setSecretKey("your-secret-key");
spec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, false); // Set to true for sandbox

StreamingExchange streamingExchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
streamingExchange.connect().blockingAwait();
```

### Market Data Subscriptions

```java
// Subscribe to order book updates
CurrencyPair pair = CurrencyPair.BTC_PHP;
Disposable orderBookSubscription = streamingExchange.getStreamingMarketDataService()
    .getOrderBook(pair)
    .subscribe(orderBook -> {
        System.out.println("Received order book: " + orderBook);
    }, throwable -> {
        System.err.println("Error in order book subscription: " + throwable.getMessage());
    });

// Subscribe to ticker updates
Disposable tickerSubscription = streamingExchange.getStreamingMarketDataService()
    .getTicker(pair)
    .subscribe(ticker -> {
        System.out.println("Received ticker: " + ticker);
    });

// Subscribe to trade updates
Disposable tradesSubscription = streamingExchange.getStreamingMarketDataService()
    .getTrades(pair)
    .subscribe(trade -> {
        System.out.println("Received trade: " + trade);
    });
```

### User Data Subscriptions

```java
// Subscribe to order updates
Disposable orderSubscription = streamingExchange.getStreamingTradeService()
    .getOrderChanges(pair)
    .subscribe(order -> {
        System.out.println("Order update: " + order);
    });

// Subscribe to user trade updates
Disposable userTradeSubscription = streamingExchange.getStreamingTradeService()
    .getUserTrades(pair)
    .subscribe(userTrade -> {
        System.out.println("User trade: " + userTrade);
    });

// Subscribe to balance updates
Disposable balanceSubscription = streamingExchange.getStreamingAccountService()
    .getBalanceChanges()
    .subscribe(balance -> {
        System.out.println("Balance update: " + balance);
    });
```

### Disconnecting

```java
// Dispose of all subscriptions
orderBookSubscription.dispose();
tickerSubscription.dispose();
tradesSubscription.dispose();
orderSubscription.dispose();
userTradeSubscription.dispose();
balanceSubscription.dispose();

// Disconnect from the WebSocket
streamingExchange.disconnect().blockingAwait();
```

## Implementation Details

The Coins.ph streaming implementation uses two WebSocket connections:
1. Public streams for market data (order books, tickers, trades)
2. Private streams for user data (orders, trades, balances) using a listenKey

The listenKey is automatically managed by the implementation, including:
- Creation on connect
- Periodic keep-alive calls
- Deletion on disconnect

## Testing

Integration tests are available in:
- `CoinsphStreamingExchangeIntegration.java` for general WebSocket functionality
- `StreamingMarketDataServiceIntegration.java` for market data streams
- `StreamingTradeServiceIntegration.java` for trade streams
- `StreamingAccountServiceIntegration.java` for account streams

To run the tests against the sandbox environment, update the API credentials in the test files.