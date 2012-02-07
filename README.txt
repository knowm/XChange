XChange
=======
XChange is library providing a simple and consistent API for interacting with a
diverse set of financial security exchanges, including support for Bitcoin. The primary 
development platform is Java, but developers are encouraged to port this code to their
own language. Ideally, these ports can be fed back into the overall XChange project so
that everyone can benefit from improvements in one central location.

In short, we invite pull requests.

Java build instructions
-----------------------
XChange Java is built with Maven 3.0.3+ (although earlier versions may work) as a reactor project.
To build everything you need, simply enter

mvn clean package

at the command line.

Maven dependency management
---------------------------
If you want to include XChange in your projects, you will need to reference one of the
Maven repositories. These are listed in the parent pom.xml. Typically you would reference the
XChange artifacts as follows:

<!-- XChange core API -->
<dependency>
  <groupId>com.xeiam.xchange</groupId>
  <artifactId>xchange</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
<!-- XChange MtGox Exchange support -->
<dependency>
  <groupId>com.xeiam.xchange</groupId>
  <artifactId>xchange-mtgox</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>

The XChange Java artifacts are currently hosted in the BitCoinJ Nexus repository here:

<repositories>
   <repository>
     <id>xchange-release</id>
     <releases/>
     <url>http://nexus.bitcoinj.org/content/repositories/releases</url>
   </repository>
   <repository>
     <id>xchange-snapshot</id>
     <snapshots/>
     <url>http://nexus.bitcoinj.org/content/repositories/snapshots</url>
   </repository>
 </repositories>


