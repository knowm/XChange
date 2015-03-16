package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * Placeholder to contain any base polling service functionality
 */
public abstract class BasePollingExchangeService extends BaseExchangeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BasePollingExchangeService(Exchange exchange) {

    super(exchange);
  }

}
