package org.knowm.xchange.mercadobitcoin.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import si.mazi.rescu.RestProxyFactory;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinAccountServiceRaw extends MercadoBitcoinBaseService {

  private static final String GET_ACCOUNT_INFO = "getInfo";

  private final MercadoBitcoinAuthenticated mercadoBitcoinAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected MercadoBitcoinAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.mercadoBitcoinAuthenticated =
        RestProxyFactory.createProxy(
            MercadoBitcoinAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getMercadoBitcoinAccountInfo()
      throws IOException {

    String method = GET_ACCOUNT_INFO;
    long tonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator =
        MercadoBitcoinDigest.createInstance(
            method,
            exchange.getExchangeSpecification().getPassword(),
            exchange.getExchangeSpecification().getSecretKey(),
            tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo =
        mercadoBitcoinAuthenticated.getInfo(
            exchange.getExchangeSpecification().getApiKey(), signatureCreator, method, tonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
