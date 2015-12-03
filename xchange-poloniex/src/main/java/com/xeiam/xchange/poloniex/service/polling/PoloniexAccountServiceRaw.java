package com.xeiam.xchange.poloniex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexException;
import com.xeiam.xchange.poloniex.dto.account.PoloniexBalance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountServiceRaw extends PoloniexBasePollingService {

    /**
     * Constructor
     *
     * @param exchange
     */
    public PoloniexAccountServiceRaw(Exchange exchange) {

        super(exchange);
    }

    public List<Balance> getWallets() throws IOException {
        try {
            HashMap<String, PoloniexBalance> response = poloniexAuthenticated.returnCompleteBalances(apiKey, signatureCreator, exchange.getNonceFactory());
            return PoloniexAdapters.adaptPoloniexBalances(response);
        } catch (PoloniexException e) {
            throw new ExchangeException(e.getError());
        }
    }

    public String getDepositAddress(String currency) throws IOException {

        HashMap<String, String> response = poloniexAuthenticated.returnDepositAddresses(apiKey, signatureCreator, exchange.getNonceFactory());

        if (response.containsKey("error")) {
            throw new ExchangeException(response.get("error"));
        }
        if (response.containsKey(currency)) {
            return response.get(currency);
        } else {
            throw new ExchangeException("Poloniex did not return a deposit address for " + currency);
        }
    }

}
