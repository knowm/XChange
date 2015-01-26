package com.xeiam.xchange.bitfinex.v1.service;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author timmolter
 */
public abstract class BitfinexBaseService extends BaseExchangeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexBaseService(Exchange exchange) {

    super(exchange);
  }

}
