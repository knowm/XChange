package com.xeiam.xchange.hitbtc.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.FundsExceededException;
import com.xeiam.xchange.exceptions.NonceException;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class HitbtcBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final HitbtcAuthenticated hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HitbtcBasePollingService(Exchange exchange) {

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
    String message = exception.getMessage();

    if ("Nonce has been used".equals(message)) {
      return new NonceException(message);
    }
    return new ExchangeException(message);
  }
}
