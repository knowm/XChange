package com.xeiam.xchange.bleutrade.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author zholmes1
 */
public abstract class BleutradeBaseService extends BaseExchangeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BleutradeBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

}
