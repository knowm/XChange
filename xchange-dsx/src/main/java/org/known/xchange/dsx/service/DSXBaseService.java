package org.known.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/**
 * @author Mikhail Wall
 */

public class DSXBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXBaseService(Exchange exchange) {
    super(exchange);


  }
}
