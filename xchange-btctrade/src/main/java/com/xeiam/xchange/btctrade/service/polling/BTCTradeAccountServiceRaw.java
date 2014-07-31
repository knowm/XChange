package com.xeiam.xchange.btctrade.service.polling;

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

  public BTCTradeBalance getBTCTradeBalance() {

    synchronized (session) {
      return btcTrade.getBalance(nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeWallet getBTCTradeWallet() {

    synchronized (session) {
      return btcTrade.getWallet(nextNonce(), publicKey, getSignatureCreator());
    }
  }
}
