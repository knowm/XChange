package org.knowm.xchange.getbtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */
public class GetbtcBaseService extends BaseExchangeService implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final String secretKey;

  protected GetbtcBaseService(Exchange exchange) {

    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.secretKey = exchange.getExchangeSpecification().getSecretKey();
  }
}
