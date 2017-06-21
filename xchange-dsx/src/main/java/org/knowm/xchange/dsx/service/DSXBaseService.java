package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticated;
import org.knowm.xchange.dsx.dto.DSXReturn;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author Mikhail Wall
 */

public class DSXBaseService extends BaseExchangeService implements BaseService {

  private static final String ERR_MSG_NONCE = "Parameter: nonce is invalid";

  protected final String apiKey;
  protected final DSXAuthenticated dsx;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXBaseService(Exchange exchange) {
    super(exchange);

    this.dsx = RestProxyFactory.createProxy(DSXAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = DSXHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected void checkResult(DSXReturn<?> result) {
    String error = result.getError();

    if (!result.isSuccess()) {
      if (error != null) {
        if (error.startsWith(ERR_MSG_NONCE)) {
          throw new NonceException(error);
        }
      }
      throw new ExchangeException(error);
    } else if (result.getReturnValue() == null) {
      throw new ExchangeException("Didn't receive any return value. Message: " + error);
    } else if (error != null) {
      throw new ExchangeException(error);
    }
  }
}
