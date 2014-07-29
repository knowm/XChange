package com.xeiam.xchange.poloniex.service.polling;

import java.util.List;

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

    List<Wallet> wallets = PoloniexAdapters.adaptPoloniexBalances(poloniex.getBalances(apiKey, signatureCreator, "returnBalances", String.valueOf(nextNonce())));
    // System.out.println("wallets: " + wallets);
    return wallets;
  }

}
