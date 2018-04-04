package org.knowm.xchange.liqui.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.liqui.dto.account.LiquiAccountInfo;

public class LiquiAccountServiceRaw extends LiquiBaseService {
  public LiquiAccountServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public LiquiAccountInfo getAccountInfoRaw() {
    return liquiAuthenticated
        .getInfo(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            "getInfo")
        .getResult();
  }
}
