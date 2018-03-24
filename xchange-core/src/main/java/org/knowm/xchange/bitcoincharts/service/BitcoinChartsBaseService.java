package org.knowm.xchange.bitcoincharts.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitcoinChartsBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinChartsBaseService(Exchange exchange) {

    super(exchange);
  }
}
