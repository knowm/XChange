package org.knowm.xchange.mercadobitcoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinBaseService(Exchange exchange) {

    super(exchange);
  }
}
