package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v2.BTCEAuthenticated;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEReturn;
import com.xeiam.xchange.btce.v2.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Matija Mazi
 */
@Deprecated
public class BTCEBasePollingService extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final BTCEAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  // counter for the nonce
  private static int lastNonce = -1;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEBasePollingService(Exchange exchange) {

    super(exchange);
    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected synchronized int nextNonce() {

    /*
     * Use time as the nonce by default (so that nonces of different processes that use the same API key are synchronized), but still increase the
     * nonce when the time component hasn't changed. See https://github.com/timmolter/XChange/issues/754 for details.
     */
    lastNonce = Math.max(lastNonce + 1, (int) ((System.currentTimeMillis() - START_MILLIS) / 250L));
    return lastNonce;
  }

  protected void checkResult(BTCEReturn<?> info) {

    if (!info.isSuccess()) {
      throw new ExchangeException(info.getError());
    } else if (info.getReturnValue() == null) {
      throw new ExchangeException("Didn't recieve any return value. Message: " + info.getError());
    } else if (info.getError() != null) {
      throw new ExchangeException(info.getError());
    }
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
