package org.knowm.xchange.bx.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.dto.account.BxBalance;
import org.knowm.xchange.bx.dto.account.results.BxBalanceResult;

public class BxAccountServiceRaw extends BxBaseService {

  public BxAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Map<String, BxBalance> getBxBalance() throws IOException {
    BxBalanceResult result =
        bx.getBalance(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    return checkResult(result);
  }
}
