package org.knowm.xchange.bitmarket.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarket;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
import org.knowm.xchange.bitmarket.BitMarketDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BitMarket bitMarket;
  protected final BitMarketAuthenticated bitMarketAuthenticated;
  protected final ParamsDigest sign;
  protected final String apiKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketBasePollingService(Exchange exchange) {
    super(exchange);

    bitMarket = RestProxyFactory.createProxy(BitMarket.class, exchange.getExchangeSpecification().getSslUri());
    bitMarketAuthenticated = RestProxyFactory.createProxy(BitMarketAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    sign = BitMarketDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
