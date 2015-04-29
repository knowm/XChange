package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;

import si.mazi.rescu.HttpStatusIOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.utils.Assert;

public class QuoineAccountServiceRaw extends QuoineBasePollingService {

  /**
   * Constructor
   */
  protected QuoineAccountServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
  }

  public QuoineAccountInfo getQuoineAccountInfo() throws IOException {

    try {
      return quoine.getAccountInfo(device, userID, userToken);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }

  }
}
