package org.knowm.xchange.coinex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinex.CoinexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CoinexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final CoinexAuthenticated coinex;
  //    protected final ParamsDigest signatureCreator;
  protected final String USER_AGENT_INFO =
      "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoinexBaseService(Exchange exchange) {
    super(exchange);
    this.coinex =
        RestProxyFactory.createProxy(
            CoinexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    //        this.signatureCreator =
    // CoinexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
