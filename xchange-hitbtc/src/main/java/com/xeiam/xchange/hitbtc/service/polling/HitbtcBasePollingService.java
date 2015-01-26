package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.FundsExceededException;
import com.xeiam.xchange.exceptions.NonceException;
import com.xeiam.xchange.hitbtc.Hitbtc;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class HitbtcBasePollingService<T extends Hitbtc> extends BaseExchangeService implements BasePollingService {

  //  protected static final String HITBTC = "hitbtc";
  //  protected static final String HITBTC_ORDER_FEE_POLICY_MAKER = HITBTC + ".order.feePolicy.maker";
  //  protected static final String HITBTC_ORDER_FEE_LISTING_DEFAULT = HITBTC + ORDER_FEE_LISTING + "default";

  protected final SynchronizedValueFactory<Long> valueFactory;

  protected final T hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param hitbtcType
   * @param exchange
   * @param nonceFactory
   */
  // TODO look at this
  protected HitbtcBasePollingService(Class<T> hitbtcType, Exchange exchange, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchange);

    this.valueFactory = nonceFactory;
    this.hitbtc = RestProxyFactory.createProxy(hitbtcType, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    String apiKey = exchange.getExchangeSpecification().getSecretKey();
    this.signatureCreator = apiKey != null && !apiKey.isEmpty() ? HitbtcHmacDigest.createInstance(apiKey) : null;
  }

  @Override
  public synchronized List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    currencyPairs.addAll(HitbtcAdapters.adaptCurrencyPairs(hitbtc.getSymbols()));
    return currencyPairs;
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
