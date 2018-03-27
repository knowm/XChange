package org.knowm.xchange.independentreserve.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** Author: Kamil Zbikowski Date: 4/9/15 */
public class IndependentReserveBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected IndependentReserveBaseService(Exchange exchange) {
    super(exchange);
  }
}
