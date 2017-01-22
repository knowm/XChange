package org.knowm.xchange.cexio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/**
 * @author timmolter
 */
public class CexIOBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOBaseService(Exchange exchange) {

    super(exchange);
  }
}
