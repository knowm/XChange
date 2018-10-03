package org.knowm.xchange.dsx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAdapters;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.dto.DSXReturn;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/** @author Mikhail Wall */
public class DSXBaseService extends BaseExchangeService implements BaseService {

  private static final String ERR_MSG_NONCE = "Parameter: nonce is invalid";

  protected final String apiKey;
  protected final DSXAuthenticatedV2 dsx;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   * @throws IOException
   */
  protected DSXBaseService(Exchange exchange) {
    super(exchange);
    this.dsx =
        RestProxyFactory.createProxy(
            DSXAuthenticatedV2.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        DSXHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    try {
      if (DSXAdapters.dsxExchangeInfo == null) {
        DSXAdapters.dsxExchangeInfo = dsx.getInfo();
      }
    } catch (IOException e) {
      throw new ExchangeException("Could not init the DSX service.", e);
    }
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
