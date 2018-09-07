package org.knowm.xchange.cryptonit2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author timmolter */
public class CryptonitBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitBaseService(Exchange exchange) {

    super(exchange);
  }

  protected ExchangeException handleError(CryptonitException exception) {

    if (exception.getMessage().contains("You can only buy")) {
      return new FundsExceededException(exception);

    } else if (exception.getMessage().contains("Invalid limit exceeded")) {
      return new RateLimitExceededException(exception);

    } else if (exception.getMessage().contains("Invalid nonce")) {
      return new NonceException(exception.getMessage());

    } else if (exception.getMessage().contains("Internal server error")) {
      return new InternalServerException(exception);

    } else {
      return new ExchangeException(exception);
    }
  }
}
