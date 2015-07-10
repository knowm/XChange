package com.xeiam.xchange.huobi.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class HuobiBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
