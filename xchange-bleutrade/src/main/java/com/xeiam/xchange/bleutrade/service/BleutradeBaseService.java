package com.xeiam.xchange.bleutrade.service;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author zholmes1
 */
public abstract class BleutradeBaseService extends BaseExchangeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeBaseService(Exchange exchange) {

    super(exchange);
  }

}
