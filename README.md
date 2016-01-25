AndroidStubServer
=================
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.androidstubserver/stubserver/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.androidstubserver/stubserver)
 master:  [![Build Status](https://travis-ci.org/byoutline/AndroidStubServer.svg?branch=master)](https://travis-ci.org/byoutline/AndroidStubServer)
 develop: [![Build Status](https://travis-ci.org/byoutline/AndroidStubServer.svg?branch=develop)](https://travis-ci.org/byoutline/AndroidStubServer)
 
Simple Http server that makes simulating API easy.

How to use
----------

* Add dependency to build.gradle:
```java
compile 'com.byoutline.androidstubserver:stubserver:1.4.1'
```
* Create ```mock``` folder in your assetes directory (```src/main/assets``` by default in gradle). 
* Put there a ```config.json``` file.
* You can find ```config.json``` description  at <a href="https://github.com/byoutline/MockServer">MockServer</a>.
* If you have any static content(like images or .html files) put them in ```mock/static``` folder.

Now you can start server by passing it context and simulated network type:
```java
AndroidStubServer.start(this, NetworkType.EDGE);
```

Placeholder images
------------------
If your API returns images urls you can also mock them.


In your config place set url as 
```http://localhost:PORT/mock/img/WIDTHxHEIGHT```

for example:

```http://localhost:8099/mock/img/560x420/ff0000/00FFFF```

will cause stub server will return png image with width of 520, height of 420, dark grey background and light grey text. 


You can also customize background color:

```http://localhost:8099/mock/img/560x420/RRGGBB```

and foreground color:

```http://localhost:8099/mock/img/560x420/RRGGBB/RRGGBB```
