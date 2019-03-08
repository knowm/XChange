package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.blockchain.Blockchain;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BlockchainBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Blockchain blockchain;

  protected BlockchainBaseService(Exchange exchange) {
    super(exchange);
    this.blockchain =
        RestProxyFactory.createProxy(
            Blockchain.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
