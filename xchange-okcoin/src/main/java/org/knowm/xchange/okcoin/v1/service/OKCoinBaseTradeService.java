package org.knowm.xchange.okcoin.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.okcoin.v1.OkCoin;
import org.knowm.xchange.okcoin.v1.OkCoinDigest;
import org.knowm.xchange.okcoin.v1.OkCoinUtils;
import org.knowm.xchange.okcoin.v1.dto.trade.OkCoinErrorResult;
import si.mazi.rescu.RestProxyFactory;

public class OKCoinBaseTradeService extends OkCoinBaseService {

  protected final OkCoin okCoin;
  protected final OkCoinDigest signatureCreator;
  protected final String apikey;
  protected final String secretKey;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OKCoinBaseTradeService(Exchange exchange) {

    super(exchange);

    okCoin = RestProxyFactory.createProxy(OkCoin.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
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
