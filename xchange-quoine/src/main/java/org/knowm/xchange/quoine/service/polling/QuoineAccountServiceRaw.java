package org.knowm.xchange.quoine.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.utils.Assert;

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
