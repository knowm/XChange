package org.knowm.xchange.bitstamp.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitstampBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
