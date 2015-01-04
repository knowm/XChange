package com.xeiam.xchange.okcoin.service.polling;

import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.okcoin.OkCoin;
import com.xeiam.xchange.okcoin.OkCoinDigest;
import com.xeiam.xchange.okcoin.OkCoinUtils;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OKCoinBaseTradePollingService extends OkCoinBasePollingService {

  protected final String apiKey;
  protected final OkCoin okCoin;
  protected final OkCoinDigest signatureCreator;

  protected OKCoinBaseTradePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Map<String, Object> specific = exchangeSpecification.getExchangeSpecificParameters();
    okCoin = RestProxyFactory.createProxy(OkCoin.class, useIntl ? (String) specific.get("Intl_SslUri") : exchangeSpecification.getSslUri());
    apiKey = exchangeSpecification.getApiKey();
    signatureCreator = new OkCoinDigest(apiKey, exchangeSpecification.getSecretKey());
  }

  protected static <T extends OkCoinErrorResult> T returnOrThrow(T t) {

    if (t.isResult()) {
      return t;
    } else {
      throw new ExchangeException(OkCoinUtils.getErrorMessage(t.getErrorCode()));
    }
  }
}
