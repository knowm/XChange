package org.knowm.xchange.btcchina.service.rest;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChina;
import org.knowm.xchange.btcchina.BTCChinaExchangeException;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.service.BTCChinaDigest;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.Assert;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class BTCChinaBaseService extends BaseExchangeService implements BaseService {

  protected final BTCChina btcChina;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaBaseService(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");

    this.btcChina = RestProxyFactory.createProxy(BTCChina.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BTCChinaDigest.createInstance(exchange.getExchangeSpecification().getApiKey(),
        exchange.getExchangeSpecification().getSecretKey());
  }

  @SuppressWarnings("rawtypes")
  public static <T extends BTCChinaResponse> T checkResult(T returnObject) {

    if (returnObject.getError() != null) {
      throw new BTCChinaExchangeException(returnObject.getError());
    } else if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
