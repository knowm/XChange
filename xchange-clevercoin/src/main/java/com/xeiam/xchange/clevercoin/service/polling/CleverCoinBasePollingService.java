package com.xeiam.xchange.clevercoin.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CleverCoinBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
