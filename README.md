XChange
=======

XChange is a library providing a simple and consistent API for interacting with a diverse set of financial security exchanges, including support for Bitcoin. The primary development platform is Java, but developers are encouraged to port this code to their own language. Ideally, these ports can be fed back into the overall XChange project so that everyone can benefit from improvements in one central location.

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
      <version>1.2.1-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-mtgox</artifactId>
      <version>1.2.1-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.xeiam.xchange</groupId>
      <artifactId>xchange-cavirtex</artifactId>
      <version>1.2.1-SNAPSHOT</version>
    </dependency>

Building
===============
mvn clean package  
mvn javadoc:javadoc 