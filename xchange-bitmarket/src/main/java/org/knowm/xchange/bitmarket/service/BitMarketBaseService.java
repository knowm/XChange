package org.knowm.xchange.bitmarket.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarket;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
import org.knowm.xchange.bitmarket.BitMarketDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketBaseService extends BaseExchangeService implements BaseService {

  protected final BitMarket bitMarket;
  protected final BitMarketAuthenticated bitMarketAuthenticated;
  protected final ParamsDigest sign;
  protected final String apiKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketBaseService(Exchange exchange) {
    super(exchange);

    bitMarket = RestProxyFactory.createProxy(BitMarket.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    bitMarketAuthenticated = RestProxyFactory.createProxy(BitMarketAuthenticated.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());
    sign = BitMarketDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
