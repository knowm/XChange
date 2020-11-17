package org.knowm.xchange.lakebtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lakebtc.LakeBTC;
import org.knowm.xchange.lakebtc.LakeBTCAuthenticated;
import org.knowm.xchange.lakebtc.dto.LakeBTCResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.ParamsDigest;

/** @author kpysniak */
public class LakeBTCBaseService extends BaseExchangeService implements BaseService {

  protected final LakeBTCAuthenticated lakeBTCAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final LakeBTC lakeBTC;

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCBaseService(Exchange exchange) {

    super(exchange);

    Assert.notNull(
        exchange.getExchangeSpecification().getSslUri(),
        "Exchange specification URI cannot be null");

    this.lakeBTCAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                LakeBTCAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.signatureCreator =
        LakeBTCDigest.createInstance(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getSecretKey());

    this.lakeBTC =
        ExchangeRestProxyBuilder.forInterface(LakeBTC.class, exchange.getExchangeSpecification())
            .build();
  }

  public static <T extends LakeBTCResponse> T checkResult(T returnObject) {

    if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
