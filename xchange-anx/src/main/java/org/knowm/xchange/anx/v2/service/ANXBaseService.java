package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.HttpStatusIOException;

public class ANXBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ANXBaseService(Exchange exchange) {

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
      return new FundsExceededException(exception.getError(), exception);
    } else {
      return new ExchangeException(exception.getError(), exception);
    }
  }
}
