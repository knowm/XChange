package org.knowm.xchange.gatecoin.service.polling;

import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
