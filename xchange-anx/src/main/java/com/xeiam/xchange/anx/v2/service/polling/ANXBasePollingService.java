package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.HttpStatusIOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.FundsExceededException;
import com.xeiam.xchange.exceptions.NonceException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class ANXBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   * @param nonceFactory
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

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
