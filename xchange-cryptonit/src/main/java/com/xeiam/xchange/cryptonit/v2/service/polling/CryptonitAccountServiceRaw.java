package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.CryptonitAuth;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoins;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFunds;

import java.io.IOException;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitAccountServiceRaw extends CryptonitBasePollingService<CryptonitAuth> {

    public CryptonitAccountServiceRaw(ExchangeSpecification exchangeSpecification) {
        super(CryptonitAuth.class, exchangeSpecification);
    }

    protected CryptonitFunds getAccountFunds() throws IOException {
        try {
            return cryptonit.getAccountFunds("Bearer " + apiKey);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

    protected CryptonitCoins getAccountCoins() throws IOException {
        try {
            return cryptonit.getAccountСoins("Bearer " + apiKey);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

}
