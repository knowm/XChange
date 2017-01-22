package org.knowm.xchange.hitbtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.hitbtc.HitbtcAuthenticated;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class HitbtcBaseService extends BaseExchangeService implements BaseService {

  protected final HitbtcAuthenticated hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HitbtcBaseService(Exchange exchange) {

    super(exchange);

    this.hitbtc = RestProxyFactory.createProxy(HitbtcAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    String apiKey = exchange.getExchangeSpecification().getSecretKey();
    this.signatureCreator = apiKey != null && !apiKey.isEmpty() ? HitbtcHmacDigest.createInstance(apiKey) : null;
  }

  protected void checkRejected(HitbtcExecutionReport executionReport) {
    if ("rejected".equals(executionReport.getExecReportType())) {
      if ("orderExceedsLimit".equals(executionReport.getOrderRejectReason())) {
        throw new FundsExceededException(executionReport.getClientOrderId());
      } else if ("exchangeClosed ".equals(executionReport.getOrderRejectReason())) {
        throw new IllegalStateException(executionReport.getOrderRejectReason());
      } else {
        throw new IllegalArgumentException("Order rejected, " + executionReport.getOrderRejectReason());
      }
    }
  }

  protected RuntimeException handleException(HitbtcException exception) {
    String code = exception.getCode();
    String message = exception.getMessage();

    if ("Nonce has been used".equals(message)) {
      return new NonceException(code + ": " + message);
    }
    return new ExchangeException(code + ": " + message);
  }
}
