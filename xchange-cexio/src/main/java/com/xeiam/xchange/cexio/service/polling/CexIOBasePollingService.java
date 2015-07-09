package com.xeiam.xchange.cexio.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CexIOBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
