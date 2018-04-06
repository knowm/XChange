package org.knowm.xchange.bx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.BxAuthenticated;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BxBaseService extends BaseExchangeService implements BaseService {

  protected final BxAuthenticated bx;
  protected final ParamsDigest signatureCreator;

  public BxBaseService(Exchange exchange) {
    super(exchange);
    bx =
        RestProxyFactory.createProxy(
            BxAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator = BxDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public <R> R checkResult(BxResult<R> bxResult) {
    if (!bxResult.isSuccess()) {
      String bxError = bxResult.getError();
      if (bxError.isEmpty()) {
        throw new ExchangeException("Missing error message");
      } else {
        throw new ExchangeException(bxError);
      }
    }
    return bxResult.getResult();
  }
}
