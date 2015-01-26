package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountServiceRaw extends PoloniexBasePollingService<PoloniexAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountServiceRaw(Exchange exchange) {

    super(PoloniexAuthenticated.class, exchange);
  }

  public List<Wallet> getWallets() throws IOException {

    HashMap<String, String> response = poloniex.returnBalances(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    } else {
      return PoloniexAdapters.adaptPoloniexBalances(response);
    }
  }

  public String getDepositAddress(String currency) throws IOException {

    HashMap<String, String> response = poloniex.returnDepositAddresses(apiKey, signatureCreator, String.valueOf(nextNonce()));

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
