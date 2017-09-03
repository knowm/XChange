package org.knowm.xchange.bitbay.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.dto.acount.BitbayAccountInfoResponse;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z. Dolezal
 */
public class BitbayAccountServiceRaw extends BitbayBaseService {

    public BitbayAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public BitbayAccountInfoResponse getBitbayAccountInfo() throws IOException {
        BitbayAccountInfoResponse response = bitbayAuthenticated.info(apiKey, sign, exchange.getNonceFactory());

        checkError(response);
        return response;
    }

    public List history(Currency currency, int limit) {
        List history = bitbayAuthenticated.history(apiKey, sign, exchange.getNonceFactory(), currency.getCurrencyCode(), limit);
        for (Object o : history) {
            System.out.println("o = " + o);
        }
        return new ArrayList();
    }
}
