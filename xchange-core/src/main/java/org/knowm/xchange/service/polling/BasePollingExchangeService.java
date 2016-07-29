package org.knowm.xchange.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;

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
