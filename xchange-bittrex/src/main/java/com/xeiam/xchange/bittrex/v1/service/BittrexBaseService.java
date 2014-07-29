package com.xeiam.xchange.bittrex.v1.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author zholmes1
 */
public abstract class BittrexBaseService extends BaseExchangeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

}