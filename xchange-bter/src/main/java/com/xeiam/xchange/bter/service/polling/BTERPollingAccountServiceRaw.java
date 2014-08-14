package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTERAuthenticated;
import com.xeiam.xchange.bter.dto.account.BTERFunds;

public class BTERPollingAccountServiceRaw extends BTERBasePollingService<BTERAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTERAuthenticated.class, exchangeSpecification);
  }

  public BTERFunds getBTERAccountInfo() throws IOException {

    BTERFunds bterFunds = bter.getFunds(exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    return handleResponse(bterFunds);
  }

}
