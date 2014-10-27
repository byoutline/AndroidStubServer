AndroidStubServer
=================

Simple Http server that makes simulating API easy.

How to use
----------

Create ```mock``` folder in your assetes directory (```src/main/assets``` by default in gradle). Put there a ```config.json``` file.
You can find ```config.json``` description  at <a href="https://github.com/byoutline/MockServer">MockServer</a>.
If you have any static content(like images or .html files) put them in ```mock/static``` folder.

Now you can start server:
```java
HttpMockServer.startMockApiServer(configReader, NetworkType.VPN);
```
