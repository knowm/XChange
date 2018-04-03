package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.dto.account.BTCTradeBalance;
import org.knowm.xchange.btctrade.dto.account.BTCTradeWallet;

public class BTCTradeAccountServiceRaw extends BTCTradeBaseTradeService {

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
