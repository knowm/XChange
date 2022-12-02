package org.knowm.xchange.mercadobitcoin.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;

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
        ExchangeRestProxyBuilder.forInterface(
                MercadoBitcoinAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getMercadoBitcoinAccountInfo()
      throws IOException {

    String method = GET_ACCOUNT_INFO;
    long nonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator =
        MercadoBitcoinDigest.createInstance(
            method,
            exchange.getExchangeSpecification().getPassword(),
            exchange.getExchangeSpecification().getSecretKey(),
            nonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo =
        mercadoBitcoinAuthenticated.getInfo(
            exchange.getExchangeSpecification().getApiKey(), signatureCreator, method, nonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
