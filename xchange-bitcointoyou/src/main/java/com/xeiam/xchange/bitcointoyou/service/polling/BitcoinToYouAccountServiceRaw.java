package com.xeiam.xchange.bitcointoyou.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAuthenticated;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.service.BitcoinToYouDigest;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author gnandiga
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouAccountServiceRaw extends BitcoinToYouBasePollingService {

  private final BitcoinToYouAuthenticated bitcoinToYouAuthenticated;

  /**
   * @param exchange
   */
  protected BitcoinToYouAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitcoinToYouAuthenticated = RestProxyFactory.createProxy(BitcoinToYouAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> getBitcoinToYouBalance() throws IOException {

    Long nonce = exchange.getNonceFactory().createValue();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchange.getExchangeSpecification().getApiKey(), exchange
        .getExchangeSpecification().getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> accountInfo = bitcoinToYouAuthenticated.getBalance(exchange.getExchangeSpecification()
        .getApiKey(), nonce, signatureCreator, nonce);

    if (accountInfo.getSuccess() == 0) {
      throw new ExchangeException("Error getting account info: " + accountInfo.getError());
    }

    return accountInfo;
  }
}
