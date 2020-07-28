package org.knowm.xchange.coinjar.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.dto.trading.CoinjarAccount;

class CoinjarAccountServiceRaw extends CoinjarBaseService {

  CoinjarAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  List<CoinjarAccount> getAccounts() throws CoinjarException, IOException {
    return coinjarTrading.getAccounts(this.authorizationHeader);
  }
}
