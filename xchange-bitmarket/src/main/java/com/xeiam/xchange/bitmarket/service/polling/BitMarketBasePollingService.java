package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarket;
import com.xeiam.xchange.bitmarket.BitMarketAuthenticated;
import com.xeiam.xchange.bitmarket.BitMarketDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

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
