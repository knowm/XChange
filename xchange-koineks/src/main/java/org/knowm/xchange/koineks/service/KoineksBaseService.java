package org.knowm.xchange.koineks.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author semihunaldi */
public class KoineksBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KoineksBaseService(Exchange exchange) {

    super(exchange);
  }
}
