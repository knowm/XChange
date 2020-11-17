package org.knowm.xchange.bitcointoyou.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.Bitcointoyou;
import org.knowm.xchange.bitcointoyou.BitcointoyouAuthenticated;
import org.knowm.xchange.bitcointoyou.service.BitcointoyouDigest;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

/** @author Jonathas Carrijo */
public class BitcointoyouBasePollingService extends BaseExchangeService implements BaseService {

  protected final Bitcointoyou bitcointoyou;
  final String apiKey;
  final BitcointoyouAuthenticated bitcointoyouAuthenticated;
  final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  BitcointoyouBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcointoyouAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitcointoyouAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitcointoyouDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(), this.apiKey);

    this.bitcointoyou =
        ExchangeRestProxyBuilder.forInterface(
                Bitcointoyou.class, exchange.getExchangeSpecification())
            .build();
  }
}
