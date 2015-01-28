package com.xeiam.xchange.okcoin.service.polling;

import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.okcoin.OkCoin;
import com.xeiam.xchange.okcoin.OkCoinDigest;
import com.xeiam.xchange.okcoin.OkCoinUtils;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OKCoinBaseTradePollingService extends OkCoinBasePollingService {

  protected final OkCoin okCoin;
  protected final OkCoinDigest signatureCreator;
  protected final String apikey;
  protected final String secretKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OKCoinBaseTradePollingService(Exchange exchange) {

    super(exchange);

    Map<String, Object> specific = exchange.getExchangeSpecification().getExchangeSpecificParameters();
    okCoin = RestProxyFactory.createProxy(OkCoin.class, useIntl ? (String) specific.get("Intl_SslUri") : exchange.getExchangeSpecification()
        .getSslUri());
    apikey = exchange.getExchangeSpecification().getApiKey();
    secretKey = exchange.getExchangeSpecification().getSecretKey();

    signatureCreator = new OkCoinDigest(apikey, secretKey);
  }

  protected static <T extends OkCoinErrorResult> T returnOrThrow(T t) {

    if (t.isResult()) {
      return t;
    } else {
      throw new ExchangeException(OkCoinUtils.getErrorMessage(t.getErrorCode()));
    }
  }
}
