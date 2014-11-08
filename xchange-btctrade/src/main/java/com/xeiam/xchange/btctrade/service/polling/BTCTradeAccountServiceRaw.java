package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;

public class BTCTradeAccountServiceRaw extends BTCTradeBaseTradePollingService {

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BTCTradeBalance getBTCTradeBalance() throws IOException {

    synchronized (session) {
      return btcTrade.getBalance(nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeWallet getBTCTradeWallet() throws IOException {

    synchronized (session) {
      return btcTrade.getWallet(nextNonce(), publicKey, getSignatureCreator());
    }
  }
}
