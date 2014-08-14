package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * <p>
 * Abstract base class to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>Provision of standard specification parsing</li>
 * </ul>
 */
public abstract class BasePollingExchangeService extends BaseExchangeService {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  protected BasePollingExchangeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

}
