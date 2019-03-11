package org.knowm.xchange.gatecoin.service;

import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author timmolter */
public class GatecoinBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinBaseService(Exchange exchange) {

    super(exchange);
  }

  protected static String getNow() {
    return String.valueOf(new BigDecimal(System.currentTimeMillis()).movePointLeft(3));
  }
}
