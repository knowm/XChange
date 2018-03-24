package org.knowm.xchange.cryptocompare.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CryptocompareBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptocompareBaseService(Exchange exchange) {

    super(exchange);
  }
}
