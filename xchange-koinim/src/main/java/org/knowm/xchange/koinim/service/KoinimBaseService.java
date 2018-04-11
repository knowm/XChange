package org.knowm.xchange.koinim.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author ahmetoz */
public class KoinimBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KoinimBaseService(Exchange exchange) {

    super(exchange);
  }
}
