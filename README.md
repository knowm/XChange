# XChange-stream [![Build Status](https://travis-ci.org/bitrich-info/xchange-stream.svg?branch=master)](https://travis-ci.org/bitrich-info/xchange-stream)
> **MAINTAINER WANTED:** If anyone would like contribute to this project and become a maintainer, let me know via message. This project would deserve more time than I have.

XChange-stream is a Java library providing a simple and consistent streaming API for interacting with Bitcoin and other crypto currency exchanges via WebSocket protocol.

It is build on top of the [XChange library](https://github.com/timmolter/XChange) providing new interfaces for the exchanges' streaming APIs. Users can subscribe for live updates via reactive streams of [RxJava library](https://github.com/ReactiveX/RxJava).

## Why use it?

- Easy to use - no need to hack WebSocket and other backends.
- Consistent & simple API across all implemented exchanges.
- Extends well-known & active Java library [XChange](http://knowm.org/open-source/xchange/).
- [Reactive streams](http://reactivex.io/) are fun to work with. 
- Modular extensibility.

## Getting started

### Include in your project

Xchange-stream is on Maven Central. You will need `xchange-stream-core` dependency and `xchange-XYZ` where XYZ is supported exchange (eg. `xchange-bitstamp`). Add the following into your `pom.xml`.


#### Maven

```xml
<dependency>
    <groupId>info.bitrich.xchange-stream</groupId>
    <artifactId>xchange-stream-core</artifactId>
    <version>x.y.z</version>
</dependency>

<dependency>
    <groupId>info.bitrich.xchange-stream</groupId>
    <artifactId>xchange-XYZ</artifactId>
    <version>x.y.z</version>
</dependency>
```

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.bitrich.xchange-stream/xchange-stream-core/badge.svg?style=flat)](https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22xchange-stream-core%22)

For snapshots version (built from develop branch), add the following repository to your `pom.xml` file.

```xml
<repository>
  <id>sonatype-oss-snapshot</id>
  <snapshots/>
  <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

#### Versioning 

XChange-stream follows major and minor version from XChange library. So version 4.2.x is based on latest 4.2. release of XChange.


### Example

Use the same library as XChange but instead of `ExchangeFactory` use `StreamingExchangeFactory` that creates `StreamingExchange` instead of `Exchange`. Then you can call `getStreamingMarketDataService` to subscribe to trade data.

```java
StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitstampStreamingExchange.class.getName());

// Connect to the Exchange WebSocket API. Blocking wait for the connection.
exchange.connect().blockingAwait();

// Subscribe to live trades update.
exchange.getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_USD)
        .subscribe(trade -> {
            LOG.info("Incoming trade: {}", trade);
        }, throwable -> {
            LOG.error("Error in subscribing trades.", throwable);
        });

// Subscribe order book data with the reference to the subscription.
Disposable subscription = exchange.getStreamingMarketDataService()
                                  .getOrderBook(CurrencyPair.BTC_USD)
                                  .subscribe(orderBook -> {
                                       // Do something
                                  });

// Unsubscribe from data order book.
subscription.dispose();

// Disconnect from exchange (non-blocking)
exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));
```
More information about reactive streams can be found at [RxJava wiki](https://github.com/ReactiveX/RxJava/wiki). 

## What is supported

Listening for live updates of

| Exchange         | order books        | trades (public)    | tickers            | balances           | trades (user)      | open orders        | notes |
| ---------------- | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ | ----- |
| **Binance**      | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | |
| **Bitfinex**     | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | |
| **Bitflyer**     | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :question:         | :question:         | :question:         | |
| **BitMEX**       | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :construction:         | :construction:         | :construction:         | We are short of maintainers able to review pull requests and provide support for Bitmex, so there is a backlog of pull requests and support is likely broken. Can you help? |
| **Bitstamp**     | :heavy_check_mark: | :heavy_check_mark: | :x:                | :question:         | :question:         | :question:         | |
| **CEX.IO**       | :heavy_check_mark: | :x:                | :x:                | :question:         | :question:         | :question:         | New support, not widely tested  |
| **Coinbase Pro** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | |
| **Coinmate**     | :heavy_check_mark: | :heavy_check_mark: | :x:                | :question:         | :question:         | :question:         | |
| **OKCoin**       | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :question:         | :question:         | :question:         | |
| **OKEx**         | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :question:         | :question:         | :question:         | |
| **Poloniex**     | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :question:         | :question:         | :question:         | |
| **Gemini**       | :heavy_check_mark: | :heavy_check_mark: | :x:                | :construction:         | :construction:         | :construction:         | |


- :heavy_check_mark: - implemented
- :construction: - missing but can be implemented
- :x: - not supported by the exchange
- :question: - exchange support level not known (please fill in)

If you missing a specific exchange implementation, feel free to propose a pull request or open an issue with some sweet BTC bounty. 
 
## Open Source Projects Using XChange-Stream

This is an, not so complete, list of projects that use XChange-Stream, feel free to add your project below.
- [XChange-Trade-Bot](https://github.com/yurivin/xchange-trade-bot) by yurivin
- [Crypto Websockets](https://github.com/firepol/crypto-websockets) by firepol
- [CryptoRealTime](https://github.com/GoogleCloudPlatform/professional-services/tree/master/examples/cryptorealtime) by galic1987
- [Orko - Multi-exchange trading](https://github.com/gruelbox/orko) by gruelbox/badgerwithagun

## License
Copyright 2017 Zdenek Dolezal, Michal Oprendek

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

