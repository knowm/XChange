package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.CryptonitAuth;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoins;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFunds;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitAccountServiceRaw extends CryptonitBasePollingService {
    protected final String apiKey;
    protected final CryptonitAuth cryptonitAuth;

    public CryptonitAccountServiceRaw(Exchange exchange) {
      super(exchange);

      this.cryptonitAuth = RestProxyFactory.createProxy(CryptonitAuth.class, exchange.getExchangeSpecification().getSslUri());
      this.apiKey = exchange.getExchangeSpecification().getApiKey();
    }

    protected CryptonitFunds getAccountFunds() throws IOException {
        try {
            return cryptonitAuth.getAccountFunds("Bearer " + apiKey);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

    protected CryptonitCoins getAccountCoins() throws IOException {
        try {
            return cryptonitAuth.getAccountСoins("Bearer " + apiKey);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

}
