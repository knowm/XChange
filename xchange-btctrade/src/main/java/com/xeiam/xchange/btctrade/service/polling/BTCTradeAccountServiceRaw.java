package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;

public class BTCTradeAccountServiceRaw extends BTCTradeBaseTradePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCTradeAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTCTradeBalance getBTCTradeBalance() throws IOException {

    synchronized (session) {
      return btcTrade.getBalance(exchange.getNonceFactory(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeWallet getBTCTradeWallet() throws IOException {

    synchronized (session) {
      return btcTrade.getWallet(exchange.getNonceFactory(), publicKey, getSignatureCreator());
    }
  }
}
