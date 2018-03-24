package org.knowm.xchange.bitcoinaverage.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitcoinAverageBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinAverageBaseService(Exchange exchange) {

    super(exchange);
  }
}
