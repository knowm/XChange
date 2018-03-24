package org.knowm.xchange.bitcoinium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitcoiniumBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoiniumBaseService(Exchange exchange) {

    super(exchange);
  }
}
