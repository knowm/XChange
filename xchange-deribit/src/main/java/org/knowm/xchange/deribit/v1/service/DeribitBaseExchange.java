package org.knowm.xchange.deribit.v1.service;

import org.knowm.xchange.deribit.v1.Deribit;
import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DeribitBaseExchange extends BaseExchangeService<DeribitExchange>
    implements BaseService {

  protected final Deribit deribit;
  protected static Integer rateLimit;
  protected static Integer rateLimitRemaining;
  protected static Long rateLimitReset;

  /**
   * Constructor
   *
   * @param exchange
   */
  public DeribitBaseExchange(DeribitExchange exchange) {

    super(exchange);
    deribit =
        RestProxyFactory.createProxy(
            Deribit.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public int getRateLimit() {
    return rateLimit;
  }

  public int getRateLimitRemaining() {
    return rateLimitRemaining;
  }

  public long getRateLimitReset() {
    return rateLimitReset;
  }
}
