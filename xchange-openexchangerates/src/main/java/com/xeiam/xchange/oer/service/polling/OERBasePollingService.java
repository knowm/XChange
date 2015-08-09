package com.xeiam.xchange.oer.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class OERBasePollingService extends BaseExchangeService implements BasePollingService {

  public OERBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
