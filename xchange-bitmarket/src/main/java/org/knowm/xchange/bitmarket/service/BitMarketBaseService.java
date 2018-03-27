package org.knowm.xchange.bitmarket.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarket;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
import org.knowm.xchange.bitmarket.BitMarketDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.ParamsDigest;

/** @author kpysniak, kfonal */
public class BitMarketBaseService extends BaseExchangeService implements BaseService {

  protected final BitMarket bitMarket;
  protected final BitMarketAuthenticated bitMarketAuthenticated;
  protected final ParamsDigest sign;
  protected final String apiKey;

  BitMarketBaseService(Exchange exchange, IRestProxyFactory restProxyFactory) {
    super(exchange);

    bitMarket =
        restProxyFactory.createProxy(
            BitMarket.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    bitMarketAuthenticated =
        restProxyFactory.createProxy(
            BitMarketAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    sign = BitMarketDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
