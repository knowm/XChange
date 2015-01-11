package com.xeiam.xchange.empoex.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author timmolter
 */
public abstract class EmpoExBaseService extends BaseExchangeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public EmpoExBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

}
