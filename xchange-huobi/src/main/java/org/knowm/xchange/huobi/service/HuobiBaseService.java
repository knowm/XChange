package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class HuobiBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiBaseService(Exchange exchange) {

    super(exchange);
  }
}
