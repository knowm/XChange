# Coins.ph Exchange

This module implements the XChange API for the [Coins.ph Exchange](https://coins.ph/).

## Info

* API Documentation: 
  * REST API: https://docs.coins.ph/rest-api/
  * WebSocket API: https://docs.coins.ph/web-socket-streams/
  * User Data Stream: https://docs.coins.ph/user-data-stream/

## Features

This implementation supports the following features:

### Market Data
* Get ticker
* Get order book
* Get trades

### Trading
* Get account info
* Get open orders
* Place market order
* Place limit order
* Place stop order
* Cancel order
* Get order status
* Get trade history

### Streaming
* Order book updates
* Ticker updates
* Trade updates
* User order updates
* User trade updates
* Balance updates

## Usage

### REST API

```java
// Create Exchange
Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinsphExchange.class);

// Configure API keys
ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
spec.setApiKey("your-api-key");
spec.setSecretKey("your-secret-key");
spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, false); // Set to true for sandbox
exchange.applySpecification(spec);

// Get market data
MarketDataService marketDataService = exchange.getMarketDataService();
Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_PHP);

// Get account info
AccountService accountService = exchange.getAccountService();
AccountInfo accountInfo = accountService.getAccountInfo();

// Place order
TradeService tradeService = exchange.getTradeService();
MarketOrder marketOrder = new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_PHP)
    .originalAmount(new BigDecimal("0.001"))
    .build();
String orderId = tradeService.placeMarketOrder(marketOrder);
```

### WebSocket API

```java
// Create Streaming Exchange
ExchangeSpecification spec = new ExchangeSpecification(CoinsphStreamingExchange.class);
spec.setApiKey("your-api-key");
spec.setSecretKey("your-secret-key");
spec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, false); // Set to true for sandbox

StreamingExchange streamingExchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
streamingExchange.connect().blockingAwait();

// Subscribe to order book updates
CurrencyPair pair = CurrencyPair.BTC_PHP;
streamingExchange.getStreamingMarketDataService()
    .getOrderBook(pair)
    .subscribe(orderBook -> {
        System.out.println("Received order book: " + orderBook);
    });

// Subscribe to user order updates
streamingExchange.getStreamingTradeService()
    .getOrderChanges(pair)
    .subscribe(order -> {
        System.out.println("Order update: " + order);
    });
```

## Testing

Integration tests are available in:
- `CoinsphExchangeIntegration.java` for REST API
- `CoinsphStreamingExchangeIntegration.java` for WebSocket API

To run the tests against the sandbox environment, update the API credentials in the test files.