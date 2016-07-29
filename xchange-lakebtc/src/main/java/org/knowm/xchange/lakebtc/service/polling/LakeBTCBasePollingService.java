package org.knowm.xchange.lakebtc.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lakebtc.LakeBTC;
import org.knowm.xchange.lakebtc.LakeBTCAuthenticated;
import org.knowm.xchange.lakebtc.dto.LakeBTCResponse;
import org.knowm.xchange.lakebtc.service.LakeBTCDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.utils.Assert;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak
 */
public class LakeBTCBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final LakeBTCAuthenticated lakeBTCAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final LakeBTC lakeBTC;

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCBasePollingService(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");

    this.lakeBTCAuthenticated = RestProxyFactory.createProxy(LakeBTCAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = LakeBTCDigest.createInstance(exchange.getExchangeSpecification().getUserName(),
        exchange.getExchangeSpecification().getSecretKey());

    this.lakeBTC = RestProxyFactory.createProxy(LakeBTC.class, exchange.getExchangeSpecification().getSslUri());

  }

  @SuppressWarnings("rawtypes")
  public static <T extends LakeBTCResponse> T checkResult(T returnObject) {

    if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
