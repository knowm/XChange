
## Bitcoin Core

This connects to the RPC port of a BitcoinCore wallet running at localhost:8332. The host and port can be changed using a custom plain text 
URI in the exchange specification. A RPC password must be setup on the BitcoinCore wallet as outlined 
[here](https://bitcoin.org/en/developer-reference#remote-procedure-calls-rpcs) and configured in the XChange implementation. 

The connection is **not** encrypted since direct remote access to the RPC port is "strongly discouraged" in preference to connecting locally as
described [here](https://en.bitcoin.it/wiki/Enabling_SSL_on_original_client_daemon).
