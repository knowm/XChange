package org.knowm.xchange.anx.v2.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.HttpStatusIOException;

public class ANXBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ANXBasePollingService(Exchange exchange) {

    super(exchange);
  }

  protected RuntimeException handleHttpError(HttpStatusIOException exception) throws IOException {
    if (exception.getHttpStatusCode() == 304) {
      return new NonceException(exception.getHttpBody());
    } else {
      throw exception;
    }
  }

  protected RuntimeException handleError(ANXException exception) {

    if ("Insufficient Funds".equals(exception.getError())) {
      return new FundsExceededException(exception.getError());
    } else {
      return new ExchangeException(exception.getError(), exception);
    }
  }
}
