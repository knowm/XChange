package org.knowm.xchange.cobinhood.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.dto.account.CobinhoodCoinBalances;

public class CobinhoodAccountServiceRaw extends CobinhoodBaseService {

  protected CobinhoodAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CobinhoodCoinBalances getCobinhoodBalances() throws IOException {
    return cobinhood.getBalances(apiKey).getResult();
  }
}
