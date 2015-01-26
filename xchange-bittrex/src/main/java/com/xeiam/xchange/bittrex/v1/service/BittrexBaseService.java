package com.xeiam.xchange.bittrex.v1.service;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author zholmes1
 */
public abstract class BittrexBaseService extends BaseExchangeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexBaseService(Exchange exchange) {

    super(exchange);
  }

}