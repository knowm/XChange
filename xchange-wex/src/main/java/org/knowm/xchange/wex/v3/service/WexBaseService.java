package org.knowm.xchange.wex.v3.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.wex.v3.WexAuthenticated;
import org.knowm.xchange.wex.v3.dto.WexReturn;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class WexBaseService extends BaseExchangeService implements BaseService {

  private static final String ERR_MSG_NONCE = "invalid nonce parameter; on key:";
  private static final String ERR_MSG_FUNDS = "It is not enough ";

  protected final String apiKey;
  protected final WexAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public WexBaseService(Exchange exchange) {

    super(exchange);

    this.btce =
        RestProxyFactory.createProxy(
            WexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        WexHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected void checkResult(WexReturn<?> result) {
    String error = result.getError();

    if (!result.isSuccess()) {
      if (error != null) {
        if (error.startsWith(ERR_MSG_NONCE)) {
          throw new NonceException(error);
        } else if (error.startsWith(ERR_MSG_FUNDS)) {
          throw new FundsExceededException(error);
        } else if (error.equals("no transactions")) {
          return; // this isn't an error - just an indicator that there's no data
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
