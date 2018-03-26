
## Ripple Fees

There are two types of fee:

* **Transaction fee** is a network charge levied in XRP. This is taken from the org.knowm.xchange.ripple.dto.account balance for each order or org.knowm.xchange.ripple.dto.trade transaction as described
[here](https://wiki.ripple.com/Transaction_Fee). This fee is put into the UserTrade fee fields. 
 
* **Transfer fee** charged by the issuer levied in the currency of traded instrument. Whoever sends an asset pays the fee, the receiver does not 
incur a charge. Not all issuers will set a fee, so some transfers may to fee free. A description of their transfer fee can be found 
[here](https://wiki.ripple.com/Transit_Fee). These fees are stored in RippleUserTrade object transfer fee fields. 

## Ripple REST API Server

Ripple Labs **strongly** advise not to use [https://api.ripple.com/](https://api.ripple.com/) for any requests that require sending a secret key. 
The Ripple REST API [documentation](https://github.com/ripple/ripple-rest) has the warning below copied at a number of different places:  

> "**DO NOT SUBMIT YOUR SECRET TO AN UNTRUSTED REST API SERVER** The secret key can be used to send transactions from your org.knowm.xchange.ripple.dto.account, 
 including spending all the balances it holds. For the public server, only use test accounts."

It also says:

> "**WARNING: If you submit your secret to a server you do not control, your org.knowm.xchange.ripple.dto.account can be stolen, along with all the money in it.** We recommend 
using a test org.knowm.xchange.ripple.dto.account with very limited funds on the public Ripple-REST server."

In a Ripple forum [discussion](https://forum.ripple.com/viewtopic.php?t=10160) someone from Ripple Labs explains:

> "While we at Ripple Labs aren't planning on stealing anyone's secret keys, we don't want people to "just trust us" instead of following proper 
security practices. We're also not protecting the server logs of our public API servers with the extreme precautions we use to protect things 
like Ripple Trade accounts. If we were to get hacked, or if one of our operations employees went rogue, any secret key you submitted to 
api.ripple.com could be used to take control of that org.knowm.xchange.ripple.dto.account. Obviously we're not planning on either of those scenarios, but it's a risk you 
don't have to take." 

The simplest solution is to run a plain text (no SSL encryption, http not https) local Ripple REST API Server that signs transactions before sending
them securely to a remote rippled server. If SSL encryption is not being used then to ensure the security of your secret key the server should be 
running locally and listening only on the localhost interface. There are instructions on how to run a local REST API Server 
[here](https://github.com/ripple/ripple-rest#quick-start) which by default connects to rippled servers at `wss://s1.ripple.com:443`. This can be 
configured using the XChange initialisation:

```
	ExchangeSpecification specification = new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(""); // remove the default api.ripple.com connection
    specification.setPlainTextUri(RippleExchange.REST_API_LOCALHOST_PLAIN_TEXT);
    specification.setSecretKey("s****************************");
    
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
```

However, if you are using a test org.knowm.xchange.ripple.dto.account, or if for some other reason you decide that you really want to trust [https://api.ripple.com/](https://api.ripple.com/)
then this must be explicitly enabled to prevent an exception being thrown on creation. This is done by setting the parameter item `trust.api.ripple.com` to `true` 
in the XChange specification, e.g.

```
    ExchangeSpecification specification = new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(RippleExchange.REST_API_RIPPLE_LABS);
    specification.setSecretKey("s****************************");
    specification.setExchangeSpecificParametersItem(RippleExchange.TRUST_API_RIPPLE_COM, true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
```

## Order Entry

If the org.knowm.xchange.ripple.dto.account does not have sufficient balance to fully execute an order, a partial (unfunded) order will be entered into the market as described 
[here](https://wiki.ripple.com/Unfunded_offers). This detail is not included in the response message so is not possible to immediately identify at  
the point of order entry. If identification of unfunded orders is important they can be found using an order book poll.    

## Trade History

The org.knowm.xchange.ripple.dto.trade history query relies on querying notifications, and then for every order type querying the corresponding hash. This has the potential to 
generate a large number of API calls and be slow. A [RippleTradeHistoryParams](src/main/java/org/knowm/xchange/ripple/service/params/RippleTradeHistoryParams.java)
object can be used to target the query more efficiently in line with the applications requirements. 
