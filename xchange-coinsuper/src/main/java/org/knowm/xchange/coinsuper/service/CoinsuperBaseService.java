package org.knowm.xchange.coinsuper.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinsuperBaseService extends BaseExchangeService implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final String secretKey;

  protected CoinsuperBaseService(Exchange exchange) {

    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.secretKey = exchange.getExchangeSpecification().getSecretKey();
  }
}
