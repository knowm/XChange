
## Bitcoin Core

This connects to the RPC port of the BitcoinCore wallet running on the localhost and default 8332 port. A RPC password must be setup 
on the BitcoinCore wallet as outlined [here](https://bitcoin.org/en/developer-reference#remote-procedure-calls-rpcs) and configured
on the XChange implementation. 

The connection is not encrypted since direct remote access to the RPC port is "strongly discouraged" in preference to connecting locally as
described [here](https://en.bitcoin.it/wiki/Enabling_SSL_on_original_client_daemon").
