package org.knowm.xchange.ccex.service.pooling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * @author Andraž Prinčič
 */
public class CCEXBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CCEXBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
