# XChange-stream [![Build Status](https://travis-ci.org/bitrich-info/xchange-stream.svg?branch=master)](https://travis-ci.org/bitrich-info/xchange-stream)
XChange-stream is a Java library providing a simple and consistent streaming API for interacting with Bitcoin and other crypto currency exchanges via WebSocket protocol.

It is build on top of of [XChange library](https://github.com/timmolter/XChange) providing new interfaces for streaming API. User can subscribe for live update via reactive streams of [RxJava library](https://github.com/ReactiveX/RxJava).

## NOTE: Library Preview

Library is in the preview state and is being prepared for the initial release. Large changes can be made in any public interface. Feel free to discuss any ideas and comments under issues section.

Anyone willing to implement support for more exchanges is welcomed.

### When will be the first release?

Soon. First release should be when [XChange](https://github.com/timmolter/XChange) version 4.1.1 will be released.

## Example

```java
StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitstampStreamingExchange.class.getName());

// Connect to the Exchange WebSocket API. Blocking wait for the connection.
exchange.connect().blockingAwait();

// Subscribe to live trades update.
exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
    LOG.info("Incoming trade: {}", trade);
});

// Subscribe order book data
Disposable subscription = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
    // Do something
});

// Unsubscribe from data
subscription.dispose();

// Disconnect from exchange (non-blocking)
exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));
```
More information about reactive streams can be found at [RxJava wiki](https://github.com/ReactiveX/RxJava/wiki). 

## Installation

TODO: Xchange-stream WILL be on Maven Central when initial release will be made. Add following into your `pom.xml`.

##### Maven

```
<dependency>
    <groupId>info.bitrich</groupId>
    <artifactId>xchange-stream</artifactId>
    <version>x.y.z</version>
</dependency>
```

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.bitrcih/xchange-stream/badge.svg?style=flat)](http://mvnrepository.com/artifact/eu.dozd/mongo-mapper)

##### Gradle

```
compile 'info.bitrich:xchange-stream:x.y.z'
```

## Licence
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

