package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountServiceRaw extends PoloniexBasePollingService<PoloniexAuthenticated> {

  public PoloniexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(PoloniexAuthenticated.class, exchangeSpecification);
  }

  public List<Wallet> getWallets() throws IOException {

    HashMap<String, String> response = poloniex.returnBalances(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.containsKey("error")) {
      throw new ExchangeException("Poloniex returned an error: " + response.get("error"));
    }
    else {
      return PoloniexAdapters.adaptPoloniexBalances(response);
    }
  }

  public String getDepositAddress(String currency) throws IOException {

    HashMap<String, String> response = poloniex.returnDepositAddresses(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.containsKey("error")) {
      throw new ExchangeException("Poloniex returned an error: " + response.get("error"));
    }
    if (response.containsKey(currency)) {
      return response.get(currency);
    }
    else {
      throw new ExchangeException("Poloniex did not return a deposit address for " + currency);
    }
  }

}
