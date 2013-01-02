XChange
=======

XChange is a library providing a simple and consistent API for interacting with a diverse set of financial security exchanges, including support for Bitcoin. A complete list of implemented exchanges, data prodiders and brokers can be found on our [Exchange Support](https://github.com/timmolter/XChange/wiki/Milestones) page. If you'd like to submit a new implementation for another exchange, please take a look at [New Implementation Best Practices](https://github.com/timmolter/XChange/wiki/New-Implementation-Best-Practices) first, as there are lots of time-saving tips! For more information such as a contributor list and a list of known projects depending on XChange, visit the [Main Project Wiki](https://github.com/timmolter/XChange/wiki). Additional information can be found by browsing through the Wiki pages. 

More Info
=========

Project Site: http://xeiam.com/xchange.jsp  
Example Code: http://xeiam.com/xchange_examplecode.jsp  
Change Log: http://xeiam.com/xchange_changelog.jsp  
Java Docs: http://xeiam.com/xchange/javadoc/index.html  

Wiki
====

Home: https://github.com/timmolter/XChange/wiki  
Design Notes: https://github.com/timmolter/XChange/wiki/Design-Notes  
Milestones: https://github.com/timmolter/XChange/wiki/Milestones  
Exchange Support: https://github.com/timmolter/XChange/wiki/Exchange-support  
Maven Integration: https://github.com/timmolter/XChange/wiki/Maven-Integration  

Getting Started
===============

Non-Maven
---------
Download Jars: http://xeiam.com/xchange.jsp

Maven
-----
The XChange artifacts are currently hosted on the Xeiam Nexus repository here:

    <repositories>
      <repository>
        <id>xchange-release</id>
        <releases/>
        <url>http://nexus.xeiam.com/content/repositories/releases</url>
      </repository>
      <repository>
        <id>xchange-snapshot</id>
        <snapshots/>
        <url>http://nexus.xeiam.com/content/repositories/snapshots/</url>
      </repository>
    </repositories>
  
Add this to dependencies in pom.xml:

    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-core</artifactId>
      <version>1.3.0-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-mtgox</artifactId>
      <version>1.3.0-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-cavirtex</artifactId>
      <version>1.3.0-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-btce</artifactId>
      <version>1.3.0-SNAPSHOT</version>
    </dependency>
        <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-bitstamp</artifactId>
      <version>1.3.0-SNAPSHOT</version>
    </dependency>
    
Building
===============
mvn clean package  
mvn javadoc:javadoc 