package com.xeiam.xchange.gatecoin.service.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class GatecoinBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinBasePollingService(Exchange exchange) {

    super(exchange);
  }

  protected static String getNow() {
    return String.valueOf(new BigDecimal(System.currentTimeMillis()).movePointLeft(3));
  }
}
