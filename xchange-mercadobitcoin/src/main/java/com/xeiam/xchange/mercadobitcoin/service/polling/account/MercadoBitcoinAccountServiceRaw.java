package com.xeiam.xchange.mercadobitcoin.service.polling.account;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAuthenticated;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.service.MercadoBitcoinDigest;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinBasePollingService;

import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * @author gnandiga
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountServiceRaw extends MercadoBitcoinBasePollingService {

  private static final String GET_ACCOUNT_INFO = "getInfo";

  private final MercadoBitcoinAuthenticated mercadoBitcoinAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected MercadoBitcoinAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.mercadoBitcoinAuthenticated = RestProxyFactory.createProxy(MercadoBitcoinAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getMercadoBitcoinAccountInfo() throws IOException {

    String method = GET_ACCOUNT_INFO;
    String tonce = MercadoBitcoinUtils.getTonce();

    MercadoBitcoinDigest signatureCreator = MercadoBitcoinDigest.createInstance(method, exchangeSpecification.getPassword(), exchangeSpecification.getSecretKey(), tonce);

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo = mercadoBitcoinAuthenticated.getInfo(exchangeSpecification.getApiKey(), signatureCreator, method, tonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
