package org.knowm.xchange.taurus.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class TaurusBasePollingService extends BaseExchangeService implements BasePollingService {

  public TaurusBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
