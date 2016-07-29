package org.knowm.xchange.quoine.service.polling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.QuoineAuthenticated;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.quoine.service.QuoineSignatureDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
    if (this.userID != null && this.secret != null) {
      this.signatureCreator = new QuoineSignatureDigest(this.userID, this.secret);
      this.contentMD5Creator = signatureCreator.getContentMD5Digester();
    } else {
      this.signatureCreator = null;
      this.contentMD5Creator = null;
    }
  }

  protected RuntimeException handleHttpError(HttpStatusIOException exception) throws IOException {

    throw new ExchangeException(exception.getHttpBody(), exception);
  }

  protected String getDate() {

    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    return format.format(new Date());
  }

  protected String getNonce() {

    return String.format("%032d", exchange.getNonceFactory().createValue());
  }
}
