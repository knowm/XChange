package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.account.AscendexCashAccountBalanceDto;

import java.io.IOException;
import java.util.List;

public class AscendexAccountServiceRaw extends AscendexBaseService {

  public AscendexAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<AscendexCashAccountBalanceDto> getBitmaxCashAccountBalance()
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.getCashAccountBalance(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator));
  }
}
