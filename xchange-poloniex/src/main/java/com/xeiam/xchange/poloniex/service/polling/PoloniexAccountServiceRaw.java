package com.xeiam.xchange.poloniex.service.polling;

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

  public List<Wallet> getWallets() {

    List<Wallet> wallets = PoloniexAdapters.adaptPoloniexBalances(poloniex.returnBalances(apiKey, signatureCreator, String.valueOf(nextNonce())));
    return wallets;
  }

  public String getDepositAddress(String currency) throws ExchangeException {

    HashMap<String, String> depositAddresses = poloniex.returnDepositAddresses(apiKey, signatureCreator, String.valueOf(nextNonce()));
    if (depositAddresses.containsKey(currency)) {
      return depositAddresses.get(currency);
    }
    else {
      throw new ExchangeException("Poloniex did not return a deposit address for " + currency);
    }
  }

}
