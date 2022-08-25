package org.knowm.xchange.coincheck.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoincheckBaseService extends BaseExchangeService implements BaseService {
  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  public CoincheckBaseService(Exchange exchange) {
    super(exchange);
  }
}
