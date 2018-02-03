package org.knowm.xchange.bitcointoyou.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.Bitcointoyou;
import org.knowm.xchange.bitcointoyou.BitcointoyouAuthenticated;
import org.knowm.xchange.bitcointoyou.service.BitcointoyouDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author Jonathas Carrijo
 */
public class BitcointoyouBasePollingService extends BaseExchangeService implements BaseService {

  final String apiKey;
  final BitcointoyouAuthenticated bitcointoyouAuthenticated;
  final ParamsDigest signatureCreator;

  protected final Bitcointoyou bitcointoyou;

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  BitcointoyouBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcointoyouAuthenticated = RestProxyFactory.createProxy(BitcointoyouAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BitcointoyouDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), this.apiKey);

    this.bitcointoyou = RestProxyFactory.createProxy(Bitcointoyou.class, exchange.getExchangeSpecification().getSslUri());

  }

}
