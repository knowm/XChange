## [![XChange](https://raw.githubusercontent.com/timmolter/XChange/develop/etc/XChange_64_64.png)](http://knowm.org/open-source/xchange) XChange

XChange is a Java library providing a simple and consistent API for interacting with 50+ Bitcoin and other crypto currency exchanges providing a consistent interface for trading and accessing market data.

## Important!

The world of Bitcoin changes quickly and XChange is no exception. The best way to stay updated on important announcements is to follow tweets on [Knowm's Twitter page](https://twitter.com/knowmorg). For the latest bugfixes and features you should use the [snapshot jars] (https://oss.sonatype.org/content/groups/public/org/knowm/xchange/) or build yourself from the DEVELOP branch. See below for more details about building with Maven. To report bugs and see what issues people are currently working on see the [issues page](https://github.com/timmolter/XChange/issues). There are also most likely some open [bounties](http://knowm.org/open-source/) to be had as well.

## Description

XChange is a library providing a simple and consistent API for interacting with a diverse set of crypto currency exchanges, including support for Bitcoin. 

A complete list of implemented exchanges, data providers and brokers can be found on our [Exchange Support](https://github.com/timmolter/XChange/wiki/Exchange-Support) page. 

Usage is very simple: Create an Exchange instance, get the appropriate service, and request data.

## Example

    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    MarketDataService marketDataService = bitstamp.getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
    
All exchange implementations expose the same API, but you can also directly access the underlying "raw" data from the individual exchanges if you need to.

Now go ahead and [study some more examples](http://knowm.org/open-source/xchange/xchange-example-code), [download the thing](http://knowm.org/open-source/xchange/xchange-change-log/) and [provide feedback](https://github.com/timmolter/XChange/issues).

## Features

* [x] MIT license
* [x] consistent API across all implemented exchanges
* [x] active development
* [x] very minimal 3rd party dependencies
* [x] modular components

## More Info

Project Site: <http://knowm.org/open-source/xchange>  
Example Code: <http://knowm.org/open-source/xchange/xchange-example-code>  
Change Log: <http://knowm.org/open-source/xchange/xchange-change-log/>  
Java Docs: <http://knowm.org/javadocs/xchange/index.html>  

Looking for streaming API? Use library [xchange-stream](https://github.com/bitrich-info/xchange-stream) based on XChange.

## Wiki

Home: https://github.com/timmolter/XChange/wiki  
Design Notes: https://github.com/timmolter/XChange/wiki/Design-Notes  
Milestones: https://github.com/timmolter/XChange/wiki/Milestones  
Exchange Support: https://github.com/timmolter/XChange/wiki/Exchange-support  
New Implementation Best Practices: https://github.com/timmolter/XChange/wiki/New-Implementation-Best-Practices
Installing SSL Certificates into TrustStore: https://github.com/timmolter/XChange/wiki/Installing-SSL-Certificates-into-TrustStore
Getting Started with XChange for Noobies: https://github.com/timmolter/XChange/wiki/Getting-Started-with-XChange-for-Noobies

## Continuous Integration

[![Build Status](https://travis-ci.org/timmolter/XChange.png?branch=develop)](https://travis-ci.org/timmolter/XChange.png)  
[Build History](https://travis-ci.org/timmolter/XChange/builds)  

## Getting Started

### Non-Maven

Download XChange Release Jars: http://search.maven.org/#search%7Cga%7C1%7Cknowm%20xchange

Download XChange Snapshot Jars: https://oss.sonatype.org/content/groups/public/org/knowm/xchange/

### Maven

The XChange release artifacts are hosted on Maven Central. 
  
Add the following dependencies in your pom.xml file. You will need at least xchange-core. Add the additional dependencies for the exchange modules you are interested in (XYZ shown only for a placeholder). There is example code for all the modules in xchange-examples.

    <dependency>
      <groupId>org.knowm.xchange</groupId>
      <artifactId>xchange-core</artifactId>
      <version>4.2.0</version>
    </dependency>
    <dependency>
      <groupId>org.knowm.xchange</groupId>
      <artifactId>xchange-examples</artifactId>
      <version>4.2.0</version>
    </dependency>
    <dependency>
      <groupId>org.knowm.xchange</groupId>
      <artifactId>xchange-XYZ</artifactId>
      <version>4.2.0</version>
    </dependency>

For snapshots, add the following repository to your pom.xml file.

    <repository>
      <id>sonatype-oss-snapshot</id>
      <snapshots/>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
    
The current snapshot version is: 

    4.2.1-SNAPSHOT
    
## Building with Maven

run unit tests: `mvn clean test`  
run unit and integration tests: `mvn clean verify -DskipIntegrationTests=false`  
install in local Maven repo: `mvn clean install`  
create project javadocs: `mvn javadoc:aggregate`  
generate dependency tree: `mvn dependency:tree`  

## Bugs

Please report any bugs or submit feature requests to [XChange's Github issue tracker](https://github.com/timmolter/XChange/issues).

## Contributing

If you'd like to submit a new implementation for another exchange, please take a look at [New Implementation Best Practices](https://github.com/timmolter/XChange/wiki/New-Implementation-Best-Practices) first, as there are lots of time-saving tips! 

For more information such as a contributor list and a list of known projects depending on XChange, visit the [Main Project Wiki](https://github.com/timmolter/XChange/wiki). 

## Donations

Donate with Bitcoin: [1JVyTP9v9z54dALuhDTZDQfS6FUjcKjPgZ](https://blockchain.info/address/1JVyTP9v9z54dALuhDTZDQfS6FUjcKjPgZ)

All donations will be used to pay bounties for new features, refactoring, etc. Please consider donating or even posting your own bounties on our [Issues Page](https://github.com/timmolter/XChange/issues?state=open). Open bounties and bounties paid thus far can be found on knowm's [bounties](http://knowm.org/open-source/) page.
