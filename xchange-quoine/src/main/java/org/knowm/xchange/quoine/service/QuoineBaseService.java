package org.knowm.xchange.quoine.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.QuoineAuthenticated;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

public class QuoineBaseService extends BaseExchangeService implements BaseService {

  protected static final int QUOINE_API_VERSION = 2;

  protected QuoineAuthenticated quoine;

  protected final QuoineSignatureDigest signatureCreator;
  protected final String contentType = "application/json";
  protected final String tokenID;
  protected final String secret;

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineBaseService(Exchange exchange) {

    super(exchange);

    quoine = RestProxyFactory.createProxy(QuoineAuthenticated.class, exchange.getExchangeSpecification().getSslUri());

    this.tokenID = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_TOKEN_ID);
    this.secret = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_USER_SECRET);

    if (this.tokenID != null && this.secret != null) {
      this.signatureCreator = new QuoineSignatureDigest(this.tokenID, this.secret, exchange.getNonceFactory());
    } else {
      this.signatureCreator = null;
    }
  }

  protected RuntimeException handleHttpError(HttpStatusIOException exception) throws IOException {

    throw new ExchangeException(exception.getHttpBody(), exception);
  }
}
