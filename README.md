## [![XChange](https://raw.githubusercontent.com/knowm/XChange/develop/etc/XChange_64_64.png)](http://knowm.org/open-source/xchange) XChange

[![Join the chat at https://gitter.im/Java-XChange/Lobby](https://badges.gitter.im/Java-XChange/Lobby.svg)](https://gitter.im/Java-XChange/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

XChange is a Java library providing a simple and consistent API for interacting with 60+ Bitcoin and other crypto currency exchanges, providing a consistent interface for trading and accessing market data.

## Important!

The world of Bitcoin changes quickly and XChange is no exception. For the latest bugfixes and features you should use the [snapshot jars](https://oss.sonatype.org/content/groups/public/org/knowm/xchange/) or build yourself from the `develop` branch. See below for more details about building with Maven. To report bugs and see what issues people are currently working on see the [issues page](https://github.com/knowm/XChange/issues).

## Description

XChange is a library providing a simple and consistent API for interacting with a diverse set of crypto currency exchanges.

Basic usage is very simple: Create an `Exchange` instance, get the appropriate service, and request data. More complex usages are progressively detailed below.

## Example 1: Public Market Data

```java
Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
MarketDataService marketDataService = bitstamp.getMarketDataService();
Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
System.out.println(ticker.toString());
```

## Example 2: Private Account Info

To use APIs which require authentication, create an `ExchangeSpecification` with your API credentials and pass this to `createExchange()`. Example:

```java
ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
exSpec.setUserName("34387");
exSpec.setApiKey("a4SDmpl9s6xWJS5fkKRT6yn41vXuY0AM");
exSpec.setSecretKey("sisJixU6Xd0d1yr6w02EHCb9UwYzTNuj");
Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec);
```

N.B.: while most exchange use an API key and secret key, others (such as username on Bitstamp or passphrase on Coinbase Pro) are exchange-specific. For more examples of adding the keys to the `ExchangeSpecification`, including storing them in a configuration file, see [Frequently Asked Questions](https://github.com/knowm/XChange/wiki/Frequently-Asked-Questions).

Once you have an authenticated `Exchange`, the private API services, `AccountService` and `TradeService`, can be used to access private data:

```java
// Get the account information
AccountService accountService = bitstamp.getAccountService();
AccountInfo accountInfo = accountService.getAccountInfo();
System.out.println(accountInfo.toString());
```

All exchange implementations expose the same API, but you can also directly access the underlying "raw" data from the individual exchanges if you need to.

## Example 3: Streaming data and websockets

The above API is usually fully supported on all exchanges and is best used for occasional requests and polling on relatively long intervals. Many exchanges, however, heavily limit the frequency that these requests can be made, and advise instead that you use their websocket API if you need up-to-the-second information.

For a smaller number of exchanges, the websocket-based `StreamingExchange` API is also available. This uses [Reactive streams](http://reactivex.io/) to allow you to efficiently subscribe to changes relating to thousands of coin pairs without requiring large numbers of threads.

You will need to import an additional dependency for the exchange you are using (see below), then example usage is as follows:

```java
// Use StreamingExchangeFactory instead of ExchangeFactory
StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitstampStreamingExchange.class.getName());

// Connect to the Exchange WebSocket API. Here we use a blocking wait.
exchange.connect().blockingAwait();

// Subscribe to live trades update.
Disposable subscription1 = exchange.getStreamingMarketDataService()
    .getTrades(CurrencyPair.BTC_USD)
    .subscribe(
        trade -> LOG.info("Trade: {}", trade),
        throwable -> LOG.error("Error in trade subscription", throwable));

// Subscribe order book data with the reference to the subscription.
Disposable subscription2 = exchange.getStreamingMarketDataService()
    .getOrderBook(CurrencyPair.BTC_USD)
    .subscribe(orderBook -> LOG.info("Order book: {}", orderBook));

// Wait for a while to see some results arrive
Thread.sleep(20000);

// Unsubscribe
subscription1.dispose();
subscription2.dispose();

// Disconnect from exchange (blocking again)
exchange.disconnect().blockingAwait();
```

Authentication, if supported for the exchange, works the same way as for the main API, via an `ExchangeSpecification`. For more information on what is supported, see the Wiki.

## More information

Now go ahead and [study some more examples](http://knowm.org/open-source/xchange/xchange-example-code), [download the thing](http://knowm.org/open-source/xchange/xchange-change-log/) and [provide feedback](https://github.com/knowm/XChange/issues).

More information about reactive streams can be found at the [RxJava wiki](https://github.com/ReactiveX/RxJava/wiki).

## Features

- [x] MIT license
- [x] consistent API across all implemented exchanges
- [x] active development
- [x] very minimal 3rd party dependencies
- [x] modular components

## More Info

Project Site: <http://knowm.org/open-source/xchange>  
Example Code: <http://knowm.org/open-source/xchange/xchange-example-code>  
Change Log: <http://knowm.org/open-source/xchange/xchange-change-log/>  
Java Docs: <http://knowm.org/javadocs/xchange/index.html>

## Wiki

- [Home](https://github.com/knowm/XChange/wiki)
- [FAQ](https://github.com/knowm/XChange/wiki/Frequently-Asked-Questions)
- [Design Notes](https://github.com/knowm/XChange/wiki/Design-Notes)
- [Milestones](https://github.com/knowm/XChange/wiki/Milestones)
- [Exchange Support](https://github.com/knowm/XChange/wiki/Exchange-support)
- [New Implementation Best Practices](https://github.com/knowm/XChange/wiki/New-Implementation-Best-Practices)
- [Installing SSL Certificates into TrustStore](https://github.com/knowm/XChange/wiki/Installing-SSL-Certificates-into-TrustStore)
- [Getting Started with XChange for Noobies](https://github.com/knowm/XChange/wiki/Getting-Started-with-XChange-for-Noobies)
- [Code Style](https://github.com/knowm/XChange/wiki/Code-Style)

## Continuous Integration

[![Build Status](https://travis-ci.org/knowm/XChange.png?branch=develop)](https://travis-ci.org/knowm/XChange.png)
[Build History](https://travis-ci.org/knowm/XChange/builds)

## Getting Started

### Non-Maven

- XChange Release Jars: http://search.maven.org/#search%7Cga%7C1%7Cknowm%20xchange
- XChange Snapshot Jars: https://oss.sonatype.org/content/groups/public/org/knowm/xchange/

### Maven

The XChange release artifacts are hosted on Maven Central.

Add the following dependencies in your pom.xml file. You will need at least xchange-core. Add the additional dependencies for the exchange modules you are interested in (XYZ shown only for a placeholder). There is example code for all the modules in xchange-examples.

```xml
<dependency>
  <groupId>org.knowm.xchange</groupId>
  <artifactId>xchange-core</artifactId>
  <version>4.4.2</version>
</dependency>
<dependency>
  <groupId>org.knowm.xchange</groupId>
  <artifactId>xchange-XYZ</artifactId>
  <version>4.4.2</version>
</dependency>
```

If it is available for your exchange, you may also want to use the streaming API:

```xml
<dependency>
  <groupId>org.knowm.xchange</groupId>
  <artifactId>xchange-stream-XYZ</artifactId>
  <version>4.4.2</version>
</dependency>
```

For snapshots, add the following repository to your pom.xml file.

```xml
<repository>
  <id>sonatype-oss-snapshot</id>
  <snapshots/>
  <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

The current snapshot version is:

    5.0.0-SNAPSHOT

## Building with Maven

run unit tests: `mvn clean test`  
run unit and integration tests: `mvn clean verify -DskipIntegrationTests=false`  
install in local Maven repo: `mvn clean install`  
create project javadocs: `mvn javadoc:aggregate`  
generate dependency tree: `mvn dependency:tree`  
check for dependency updates: `mvn versions:display-dependency-updates`  
check for plugin updates: `mvn versions:display-plugin-updates`  
code format: `mvn com.coveo:fmt-maven-plugin:format`

## Bugs

Please report any bugs or submit feature requests to [XChange's Github issue tracker](https://github.com/knowm/XChange/issues).

## Contributing

If you'd like to submit a new implementation for another exchange, please take a look at [New Implementation Best Practices](https://github.com/knowm/XChange/wiki/New-Implementation-Best-Practices) first, as there are lots of time-saving tips!

For more information such as a contributor list and a list of known projects depending on XChange, visit the [Main Project Wiki](https://github.com/knowm/XChange/wiki).
