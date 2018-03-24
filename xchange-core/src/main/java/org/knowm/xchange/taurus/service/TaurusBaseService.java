package org.knowm.xchange.taurus.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/**
 * @author timmolter
 */
public class TaurusBaseService extends BaseExchangeService implements BaseService {

  public TaurusBaseService(Exchange exchange) {
    super(exchange);
  }
}
