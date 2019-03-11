package org.knowm.xchange.okcoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.okcoin.OkCoin;
import org.knowm.xchange.okcoin.OkCoinDigest;
import org.knowm.xchange.okcoin.OkCoinUtils;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;
import si.mazi.rescu.RestProxyFactory;

public class OKCoinBaseTradeService extends OkCoinBaseService {

  protected final OkCoin okCoin;
  protected final String apikey;
  protected final String secretKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OKCoinBaseTradeService(Exchange exchange) {

    super(exchange);

    okCoin =
        RestProxyFactory.createProxy(
            OkCoin.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    apikey = exchange.getExchangeSpecification().getApiKey();
    secretKey = exchange.getExchangeSpecification().getSecretKey();
  }

  protected OkCoinDigest signatureCreator() {
    return new OkCoinDigest(apikey, secretKey);
  }

  protected static <T extends OkCoinErrorResult> T returnOrThrow(T t) {

    if (t.isResult()) {
      return t;
    } else {
      throw new ExchangeException(OkCoinUtils.getErrorMessage(t.getErrorCode()));
    }
  }
}
