package com.xeiam.xchange.mercadobitcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.service.MercadoBitcoinDigest;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountServiceRaw extends MercadoBitcoinBasePollingService {

  private static final String GET_ACCOUNT_INFO = "getInfo";

  private final MercadoBitcoinAuthenticated mercadoBitcoinAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected MercadoBitcoinAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.mercadoBitcoinAuthenticated = RestProxyFactory.createProxy(MercadoBitcoinAuthenticated.class,
        exchange.getExchangeSpecification().getSslUri());
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getMercadoBitcoinAccountInfo() throws IOException {

    String method = GET_ACCOUNT_INFO;
    long tonce = exchange.getNonceFactory().createValue();

    MercadoBitcoinDigest signatureCreator = MercadoBitcoinDigest.createInstance(method, exchange.getExchangeSpecification().getPassword(),
        exchange.getExchangeSpecification().getSecretKey(), tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo = mercadoBitcoinAuthenticated
        .getInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator, method, tonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
