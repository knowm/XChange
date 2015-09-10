package com.xeiam.xchange.taurus.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class TaurusBasePollingService extends BaseExchangeService implements BasePollingService {

  public TaurusBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
