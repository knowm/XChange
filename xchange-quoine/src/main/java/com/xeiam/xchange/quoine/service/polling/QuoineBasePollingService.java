package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.quoine.QuoineAuthenticated;
import com.xeiam.xchange.quoine.QuoineExchange;
import com.xeiam.xchange.quoine.service.QuoineSignatureDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class QuoineBasePollingService extends BaseExchangeService implements BasePollingService {

  protected QuoineAuthenticated quoine;

  protected final QuoineSignatureDigest signatureCreator;
  protected final ParamsDigest contentMD5Creator;
  protected final String contentType = "application/json";
  protected final String userID;
  protected final String secret;

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineBasePollingService(Exchange exchange) {

    super(exchange);

    quoine = RestProxyFactory.createProxy(QuoineAuthenticated.class, exchange.getExchangeSpecification().getSslUri());

    this.userID = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_USER_ID);
    this.secret = (String) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(QuoineExchange.KEY_USER_SECRET);
    this.signatureCreator = new QuoineSignatureDigest(this.userID, this.secret);
    this.contentMD5Creator = signatureCreator.getContentMD5Digester();
  }

  protected RuntimeException handleHttpError(HttpStatusIOException exception) throws IOException {

    throw new ExchangeException(exception.getHttpBody(), exception);
  }

  protected String getDate() {

    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    return format.format(new Date());
  }

  protected String getNonce() {

    return String.format("%032d", String.valueOf(exchange.getNonceFactory().createValue()));
  }
}
