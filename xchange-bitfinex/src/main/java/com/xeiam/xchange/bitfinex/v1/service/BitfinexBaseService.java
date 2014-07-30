package com.xeiam.xchange.bitfinex.v1.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author timmolter
 */
public abstract class BitfinexBaseService extends BaseExchangeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

}
