package org.knowm.xchange.bitstamp.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/**
 * @author timmolter
 */
public class BitstampBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampBaseService(Exchange exchange) {

    super(exchange);
  }
}
