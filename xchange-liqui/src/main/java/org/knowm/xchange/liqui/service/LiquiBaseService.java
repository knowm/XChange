package org.knowm.xchange.liqui.service;

import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.liqui.Liqui;
import org.knowm.xchange.liqui.LiquiAuthenticated;
import org.knowm.xchange.liqui.dto.LiquiException;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class LiquiBaseService extends BaseExchangeService implements BaseService {

  protected final Liqui liqui;
  protected final LiquiAuthenticated liquiAuthenticated;
  protected final LiquiDigest signatureCreator;

  protected LiquiBaseService(final Exchange exchange) {
    super(exchange);

    liqui =
        RestProxyFactory.createProxy(
            Liqui.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    liquiAuthenticated =
        RestProxyFactory.createProxy(
            LiquiAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    signatureCreator =
        LiquiDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public Map<String, LiquiPairInfo> getInfo() {
    return checkResult(liqui.getInfo());
  }

  protected <R> R checkResult(final LiquiResult<R> liquiResult) {
    if (liquiResult.getError() != null) {
      throw new LiquiException(liquiResult.getError());
    }
    if (liquiResult.getResult() == null) {
      throw new LiquiException("Result is missing");
    }

    return liquiResult.getResult();
  }
}
