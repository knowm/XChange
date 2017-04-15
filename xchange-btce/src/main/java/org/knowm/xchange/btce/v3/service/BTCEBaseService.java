package org.knowm.xchange.btce.v3.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAuthenticated;
import org.knowm.xchange.btce.v3.dto.BTCEReturn;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BTCEBaseService extends BaseExchangeService implements BaseService {

  private static final String ERR_MSG_NONCE = "invalid nonce parameter; on key:";
  private static final String ERR_MSG_FUNDS = "It is not enough ";

  protected final String apiKey;
  protected final BTCEAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEBaseService(Exchange exchange) {

    super(exchange);

    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected void checkResult(BTCEReturn<?> result) {
    String error = result.getError();

    if (!result.isSuccess()) {
      if (error != null) {
        if (error.startsWith(ERR_MSG_NONCE)) {
          throw new NonceException(error);
        } else if (error.startsWith(ERR_MSG_FUNDS)) {
          throw new FundsExceededException(error);
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
