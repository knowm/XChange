package com.xeiam.xchange.poloniex.service;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author Zach Holmes
 */

public abstract class PoloniexBaseService extends BaseExchangeService {

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public PoloniexBaseService(Exchange exchange) {

    super(exchange);
  }

}
