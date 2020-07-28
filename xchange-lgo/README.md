## LGO 

Supports LGO HTTP API and authentication. 

For now, only metadata, auth, trade history, order book, order signature and placing orders/cancelling orders are supported. 
The web socket API should be favored (lowest possible latency).

For web socket support, see [xchange-stream](https://github.com/lgo-public/xchange-stream)

### Documentation

[LGO web site](https://lgo.group)

[API Documentation](https://doc.exchange.lgo.markets)

### Authentication methods

LGO authenticates request using an api key, and a RSA key/pair.
This library supports several strategy do deal with that. 

You can choose between the two methods by setting the `LgoEnv.SIGNATURE_SERVICE` specific parameter to `SignatureService.PASSTHROUGHS` or `SignatureService.LOCAL_RSA`.
`LOCAL_RSA` is assumed by default. 


#### PASSTHROUGHS

This strategy is intended for people running this lib behind a proxy that will do the real authentication.
This is a good approach to secure your secrets, and even more so if you managed them with an HSM. 

This strategy will pass in the Authorization header whatever you gave as `User_Id` `Api_Key`
 and `Secret_Key`, encoded in Base64, concatenated as so : `LGO ${User_Id}:${Api_Key}:${Secret_Key}
 
Your proxy could then fetch the real key given this identifier, and do the authorization
 
#### Local RSA

This strategy uses directly your secrets to apply authentication. Bet very careful about how you store and secure your secrets.

You should configure `ExchangeSpecification` by setting `setApiKey` with your accessKey, and `setApiSecret` with your private key in PKCS8 format.

##### How to generate a proper private key for real usage or integration tests
 
To generate a valid key pair : 

* `openssl genrsa -out private.pem 2048`  (generates a 2048 RSA private key in PKCS1)
* `openssl rsa -in private.pem -pubout > key.pub` (deduce your pub key from private key)
* `openssl pkcs8 -topk8 -inform PEM -in private.pem -out private_key.pem -nocrypt` (converts your private key to PKCS8)  


In LGO application, to generate an api key, copy the content of `key.pub`, and paste it when asked. 
You should be given an api key. 

This library expect your private key in PKCS8 format (the second file you generated earlier).

For integration tests, an integration directory should exist into src/test/resources, containing: 
* `api_key.txt` : a file containing your api key in plain text
* `private_key.pem` : the private key in PKCS8 pem format generated earlier

### Order encryption

LGO includes an optional front-running protection. More informations are available [here](https://lgono.de/) 
In practice, to prevent front-running, you can encrypt your orders with a key LGO doesn't know, and your order will be unencrypted only after LGO made several proofs that it won't 
insert orders before yours. 

This behavior is controlled by the specific parameter `LgoEnv.SHOULD_ENCRYPT_ORDERS`, that can be set to `true` or `false`.  