## [![XChange](http://xeiam.com/static/xeiamweb/images/XChange_64_64.png)](http://xeiam.com/xchange) XChange
XChange is a Java library providing a simple and consistent API for interacting with over a dozen Bitcoin exchanges providing a consistent interface for trading and accessing market data.

## Important!
The world of Bitcoin changes quickly and XChange is no exception. The best way to stay updated on important announcements is to follow tweets on [Xeiam's Twitter page](https://twitter.com/Xeiam). For the latest bugfixes and features you should use the [snapshot jars] (https://oss.sonatype.org/content/groups/public/com/xeiam/xchange/) or build yourself from the DEVELOP branch. See below for more details about building with Maven. To report bugs and see what issues people are currently working on see the [issues page](https://github.com/timmolter/XChange/issues). There are also most likely some open [bounties](http://xeiam.com/bounties) to be had as well.

## Description
XChange is a library providing a simple and consistent API for interacting with a diverse set of financial security exchanges, including support for Bitcoin. 

A complete list of implemented exchanges, data providers and brokers can be found on our [Exchange Support](https://github.com/timmolter/XChange/wiki/Exchange-Support) page. 

Usage is very simple: Create an Exchange instance, get the appropriate service, and request data.

## Example

    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    PollingMarketDataService marketDataService = bitstamp.getPollingMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
    
All exchange implementations expose the same API, but you can also directly access the raw data.

Now go ahead and [study some more examples](http://xeiam.com/xchange_examplecode.jsp), [download the thing](http://xeiam.com/xchange_changelog.jsp) and [provide feedback](https://github.com/timmolter/XChange/issues).

## Features
* MIT license
* consistent API across all implemented exchanges
* active development
* very minimal 3rd party dependencies
* modular components
* polling and streaming capability
    
## More Info
Project Site: http://xeiam.com/xchange  
Example Code: http://xeiam.com/xchange_examplecode.jsp  
Change Log: http://xeiam.com/xchange_changelog.jsp  
Java Docs: http://xeiam.com/xchange/javadoc/index.html  

## Wiki
Home: https://github.com/timmolter/XChange/wiki  
Design Notes: https://github.com/timmolter/XChange/wiki/Design-Notes  
Milestones: https://github.com/timmolter/XChange/wiki/Milestones  
Exchange Support: https://github.com/timmolter/XChange/wiki/Exchange-support  
New Implementation Best Practices: https://github.com/timmolter/XChange/wiki/New-Implementation-Best-Practices

## Continuous Integration
[![Build Status](https://travis-ci.org/timmolter/XChange.png?branch=develop)](https://travis-ci.org/timmolter/XChange.png)  
[Build History](https://travis-ci.org/timmolter/XChange/builds)  

## Getting Started
XChange is semantically versioned: http://semver.org  

### Non-Maven
Download XChange Release Jars: http://xeiam.com/xchange
Download XChange Snapshot Jars: https://oss.sonatype.org/content/groups/public/com/xeiam/xchange/

#### Compile Dependencies
    +- org.java-websocket:Java-WebSocket:jar:1.3.0:compile
    +- org.slf4j:slf4j-api:jar:1.7.5:compile
    +- com.github.mmazi:rescu:jar:1.6.0:compile
    |  +- com.fasterxml.jackson.core:jackson-core:jar:2.1.1:compile
    |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.1.1:compile
    |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.1.1:compile
    |  +- javax.ws.rs:jsr311-api:jar:1.1.1:compile
    |  \- com.google.code.findbugs:jsr305:jar:2.0.1:compile

#### Test Dependencies
    +- junit:junit:jar:4.11:test
    |  \- org.hamcrest:hamcrest-core:jar:1.3:test
    +- org.easytesting:fest-assert-core:jar:2.0M10:test
        \- org.easytesting:fest-util:jar:1.2.5:test
   
#### Other Dependencies for Some Examples
    +- ch.qos.logback:logback-classic:jar:1.0.13:runtime
    |  \- ch.qos.logback:logback-core:jar:1.0.13:runtime
    +- org.java-websocket:Java-WebSocket:jar:1.3.0:compile
    +- com.pusher:pusher-java-client:jar:0.3.1:compile
    |   \- com.google.code.gson:gson:jar:2.2.2:compile
    +- org.bouncycastle:bcprov-jdk15on:jar:1.50:compile
    +- com.xeiam.xchart:xchart:jar:2.3.2:compile   

### Maven
The XChange release artifacts are hosted on Maven Central. 
  
Add the following dependencies in your pom.xml file. You will need at least xchange-core. Add the additional dependencies for the exchange modules you are interested in (XYZ shown only for a placeholder). There is example code for all the modules in xchange-examples.

    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-core</artifactId>
      <version>2.0.0</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-examples</artifactId>
      <version>2.0.0</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-XYZ</artifactId>
      <version>2.0.0</version>
    </dependency>
 
    
For snapshots, add the following repository to your pom.xml file.

    <repository>
      <id>sonatype-oss-snapshot</id>
      <snapshots/>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
    
The current snapshot version is: 

    2.1.0-SNAPSHOT
    
## Building with Maven

install in local Maven repo: `mvn clean install`  
create project javadocs: `mvn javadoc:aggregate`  
generate dependency tree: `mvn dependency:tree`  
check/update all header files: `mvn license:check` and `mvn license:format`

## Bugs
Please report any bugs or submit feature requests to [XChange's Github issue tracker](https://github.com/timmolter/XChange/issues).

## Contributing
If you'd like to submit a new implementation for another exchange, please take a look at [New Implementation Best Practices](https://github.com/timmolter/XChange/wiki/New-Implementation-Best-Practices) first, as there are lots of time-saving tips! 

For more information such as a contributor list and a list of known projects depending on XChange, visit the [Main Project Wiki](https://github.com/timmolter/XChange/wiki). 

## Donations

[1MHMpzFxx4fRSaeYGSxhyEcgux7j4Gqwsc](https://blockchain.info/address/1MHMpzFxx4fRSaeYGSxhyEcgux7j4Gqwsc)

All donations will be used to pay bounties for new features, refactoring, etc. Please consider donating or even posting your own bounties on our [Issues Page](https://github.com/timmolter/XChange/issues?state=open). Open bounties and bounties paid thus far can be found on Xeiam's [bounties](http://xeiam.com/bounties) page.