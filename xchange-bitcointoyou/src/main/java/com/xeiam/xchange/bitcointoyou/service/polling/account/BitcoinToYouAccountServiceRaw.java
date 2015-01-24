package com.xeiam.xchange.bitcointoyou.service.polling.account;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAuthenticated;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouUtils;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.service.BitcoinToYouDigest;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouBasePollingService;
import com.xeiam.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * @author gnandiga
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouAccountServiceRaw extends BitcoinToYouBasePollingService {

  private final BitcoinToYouAuthenticated bitcoinToYouAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitcoinToYouAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.bitcoinToYouAuthenticated = RestProxyFactory.createProxy(BitcoinToYouAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> getBitcoinToYouBalance() throws IOException {

    Long nonce = BitcoinToYouUtils.getNonce();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> accountInfo = bitcoinToYouAuthenticated.getBalance(exchangeSpecification.getApiKey(), nonce, signatureCreator, nonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
