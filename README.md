# XChange polyglot fork


goal
=====

this branch is for {lombok, swagger, kotlin, scala, android, maven code generators, gradle contributors, camel, actors, erlang, nosql} hackers who want to push the envelope and pursue (reducto-absurdum)-1 towards scalable and efficient real time trading methods and concise code representations

methodology/seed build
=====
initially this uses a script to merge xchange-core and the style-guide compliant modules and should retain the ability to absorb development branch modifications as a baseline (see bin/bootstrap to start your own!)  this gives a noticeable build time improvement and simplifies debugging and hot reloading visibility by the tools.

bootstrapping
=============

if done right you can be done in less time than a single XChange build

in bash, (not windows bash) run bin/bootstrap (ssd helps)
```bash
git clone git@github.com:jnorthrup/XChange.git
git clone XChange -b Xpolyglot  Xpolyglot
cd Xpolyglot
bin/bootstrap
mvn clean install
```

```
[INFO] Reactor Summary:
[INFO]
[INFO] xchange-parent ..................................... SUCCESS [  0.643 s]
[INFO] XChange Speedball .................................. SUCCESS [ 38.055 s]
[INFO] XChange {bitcoincoid} generated .................... SUCCESS [  9.487 s]
[INFO] XChange {cryptocompare} lombok ..................... SUCCESS [ 11.521 s]
[INFO] XChange {huobipro} generated ....................... SUCCESS [  5.724 s]
[INFO] XChange {idex} generated ........................... SUCCESS [  7.191 s]
[INFO] XChange {stocksexchange} generated ................. SUCCESS [  2.809 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
```

testing
======
we know that the XChange/develop branch tests are at a certain state, though the json test resources lack package isolation and there are collisions when merged.  for this reasons the test phase of maven should be de-emphasized until enough compartmentalization exists in the parent fork.  these are not hard to fix, but there's a large and growing number of cut and paste module artifacts with un-mergable structure at this time.

coding standards
======
https://12factor.net and microservices aren't identical, but they are close cousins, and make good guidelines to supercede any manually evangelized set of coding/style guidelines with real-world examples like netflix and heroku ecosystems.  minimizing "bikeshedding" on indentation, module structure, and library increases the available time for someone with a vision to illustrate by doing, and forking if there are irreconcilable differences.

Github Guide
============
["How GitHub Uses GitHub to Build GitHub by Zach Holman"](  https://www.youtube.com/watch?v=qyz3jkOBbQY )

Jitpack
=======
Jitpack is handy for developing custom forks.
e.g. as of this writing this branch uses (subject to change, but a guideline)
```xml

    <repositories>
     <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
     </repository>
    </repositories>

    <dependency>
     <groupId>com.github.jnorthrup.XChange</groupId>
     <artifactId>xchange-all</artifactId>
     <version>4.3.5-SNAPSHOT</version>
    </dependency>

```
...the jury is out on jitpack

