# XChange-stream [![Build Status](https://travis-ci.org/bitrich-info/xchange-stream.svg?branch=master)](https://travis-ci.org/bitrich-info/xchange-stream)
XChange-stream is a Java library providing a simple and consistent streaming API for interacting with Bitcoin and other crypto currency exchanges via WebSocket protocol.

It is build on top of of [XChange library](https://github.com/timmolter/XChange) providing new interfaces for streaming API. User can subscribe for live update via reactive streams of [RxJava library](https://github.com/ReactiveX/RxJava).

## Why use it?

- Easy to use - no need to hack WebSocket and other backends.
- Consistent & simple API across all implemented exchanges.
- Extends well-known & active Java library [XChange](http://knowm.org/open-source/xchange/).
- [Reactive streams](http://reactivex.io/) are fun to work with. 
- Modular extensibility.

## Getting started

### Include in your project

Xchange-stream is on Maven Central. You will need `xchange-stream-core` dependency and `xchange-XYZ` where XYZ is supported exchange (eg. `xchange-bitstamp`). Add following into your `pom.xml`.


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

Xchange-stream follows major and minor version from XChange library. So version 4.2.x is based on latest 4.2. release of XChange.


### Example

Use the library same as the XChange. But instead of `ExchangeFactory` use `StreamingExchangeFactory` that creates `StreamingExchange` instead of `Exchange`. Then you can call `getStreaminMarkeDataService` as well as `getPolling*Service`.

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

Exchange | order books | trades | tickers
-------- | ----------- | ------ | -------
**Binance** | :heavy_check_mark: | :construction: | :heavy_check_mark:
**Bitfinex** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**Bitflyer** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**BitMEX** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**Bitstamp** | :heavy_check_mark: | :heavy_check_mark: | :x:
**Coinmate** | :heavy_check_mark: | :heavy_check_mark: | :x:
**OKCoin** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**OKEx** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**Poloniex** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**GDAX** | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark:
**Gemini** | :heavy_check_mark: | :heavy_check_mark: | :x:
**Wex** | :heavy_check_mark: | :heavy_check_mark: | :x:

- :heavy_check_mark: - implemented
- :construction: - missing but can be implemented
- :x: - not supported by the exchange

GDAX authenticated live updates is not yet supported, hopefully it will be added in the near future. 

If you missing specific exchange implementation, feel free to propose pull request or open issue with some sweet BTC bounty. 
 

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

