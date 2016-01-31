package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import com.xeiam.xchange.utils.Assert;

import si.mazi.rescu.HttpStatusIOException;

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
      return quoine.getAccountInfo(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public QuoineTradingAccountInfo[] getQuoineTradingAccountInfo() throws IOException {

    try {
      return quoine.getTradingAccountInfo(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }
}
