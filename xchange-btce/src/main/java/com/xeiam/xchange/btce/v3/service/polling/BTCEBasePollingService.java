package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.xeiam.xchange.FundsExceededException;
import com.xeiam.xchange.NonceException;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BTCEBasePollingService<T extends BTCE> extends BaseExchangeService implements BasePollingService {

  protected static final String PREFIX = "btce";
  protected static final String KEY_ORDER_SIZE_SCALE_DEFAULT = PREFIX + SUF_ORDER_SIZE_SCALE_DEFAULT;

  private static final String ERR_MSG_NONCE = "invalid nonce parameter; on key:";
  private static final String ERR_MSG_FUNDS = "It is not enough ";

  public final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  private SynchronizedValueFactory<Integer> nonceFactory;

  protected final String apiKey;
  protected final T btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBasePollingService(Class<T> btceType, ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Integer> nonceFactory) {

    super(exchangeSpecification);

    this.btce = RestProxyFactory.createProxy(btceType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
    this.nonceFactory = nonceFactory;
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      currencyPairs.addAll(BTCEAdapters.adaptCurrencyPairs(btce.getInfo().getPairs().keySet()));
    }

    return currencyPairs;
  }

  protected SynchronizedValueFactory<Integer> nextNonce() {

    return nonceFactory;
  }

  protected void checkResult(BTCEReturn<?> result) {
    String error = result.getError();

    if (!result.isSuccess()) {
      if (error != null) {
        if (error.startsWith(ERR_MSG_NONCE))
          throw new NonceException(error);
        else if (error.startsWith(ERR_MSG_FUNDS))
          throw new FundsExceededException(error);
      }
      throw new ExchangeException(error);
    }

    else if (result.getReturnValue() == null) {
      throw new ExchangeException("Didn't receive any return value. Message: " + error);
    }

    else if (error != null) {
      throw new ExchangeException(error);
    }
  }

}
