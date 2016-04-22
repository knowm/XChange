package org.knowm.xchange.oer.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class OERBasePollingService extends BaseExchangeService implements BasePollingService {

  public OERBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
