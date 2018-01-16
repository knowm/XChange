package org.knowm.xchange.bitstamp.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.exceptions.*;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/**
 * @author timmolter
 */
public class BitstampBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampBaseService(Exchange exchange) {

    super(exchange);
  }



  protected ExchangeException handleError(BitstampException exception) {

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
