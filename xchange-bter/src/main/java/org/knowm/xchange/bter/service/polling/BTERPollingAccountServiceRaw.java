package org.knowm.xchange.bter.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.dto.account.BTERFunds;

public class BTERPollingAccountServiceRaw extends BTERBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERPollingAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTERFunds getBTERAccountInfo() throws IOException {

    BTERFunds bterFunds = bter.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return handleResponse(bterFunds);
  }

}
