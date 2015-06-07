
## Ripple REST API Servers

Ripple Labs **strongly** advise not to use [https://api.ripple.com/](https://api.ripple.com/) for any requests that require sending a secret key. 
The Ripple REST API [documentation](https://github.com/ripple/ripple-rest) has the warning below copied at a number of different places:  

> "**DO NOT SUBMIT YOUR SECRET TO AN UNTRUSTED REST API SERVER** The secret key can be used to send transactions from your account, 
 including spending all the balances it holds. For the public server, only use test accounts."

It also says:

> "**WARNING: If you submit your secret to a server you do not control, your account can be stolen, along with all the money in it.** We recommend 
using a test account with very limited funds on the public Ripple-REST server."

In a Ripple forum [discussion](https://forum.ripple.com/viewtopic.php?t=10160) someone from Ripple Labs explains:

> "While we at Ripple Labs aren't planning on stealing anyone's secret keys, we don't want people to "just trust us" instead of following proper 
security practices. We're also not protecting the server logs of our public API servers with the extreme precautions we use to protect things 
like Ripple Trade accounts. If we were to get hacked, or if one of our operations employees went rogue, any secret key you submitted to 
api.ripple.com could be used to take control of that account. Obviously we're not planning on either of those scenarios, but it's a risk you 
don't have to take." 

The simplest solution is to run a plain text (no SSL encryption, http not https) local Ripple REST API Server that signs transactions before sending
them securely to a remote rippled server.  If SSL encryption is not being used then to ensure the security of your secret key the server should be 
running locally and listening only on the localhost interface. There are instructions on how to run a local REST API Server 
[here](https://github.com/ripple/ripple-rest#quick-start) which by default connects to rippled servers at `wss://s1.ripple.com:443`. This can be 
connected to using the initialisation:

```
	ExchangeSpecification specification = new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(""); // remove the default api.ripple.com connection
    specification.setPlainTextUri(RippleExchange.REST_API_LOCALHOST_PLAIN_TEXT);
    specification.setSecretKey("s****************************");
    
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
```

However, if you are using a test account, or if for some other reason you decide that you really want to trust [https://api.ripple.com/](https://api.ripple.com/)
then this must be explicitly enabled by setting the parameter item `trust.api.ripple.com` to `true` in the exchange specification e.g.

```
    ExchangeSpecification specification = new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(RippleExchange.REST_API_RIPPLE_LABS);
    specification.setSecretKey("s****************************");
    specification.setExchangeSpecificParametersItem(RippleExchange.TRUST_API_RIPPLE_COM, true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
```
