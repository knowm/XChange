package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.BTERAuthenticated;
import com.xeiam.xchange.bter.dto.account.BTERFunds;

public class BTERPollingAccountServiceRaw extends BTERBasePollingService<BTERAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERPollingAccountServiceRaw(Exchange exchange) {

    //TODO look at this
    super(BTERAuthenticated.class, exchange);
  }

  public BTERFunds getBTERAccountInfo() throws IOException {

    BTERFunds bterFunds = bter.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator, nextNonce());
    return handleResponse(bterFunds);
  }

}
