package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;

public class BTCTradeTradeServiceRaw extends BTCTradeBaseTradePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCTradeTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTCTradeOrder[] getBTCTradeOrders(long since, String type) throws IOException {

    synchronized (session) {
      return btcTrade.getOrders(since, type, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeOrder getBTCTradeOrder(String id) throws IOException {

    synchronized (session) {
      return btcTrade.getOrder(id, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeResult cancelBTCTradeOrder(String id) throws IOException {

    synchronized (session) {
      return btcTrade.cancelOrder(id, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradePlaceOrderResult buy(BigDecimal amount, BigDecimal price) throws IOException {

    synchronized (session) {
      return btcTrade.buy(amount.toPlainString(), price.toPlainString(), nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradePlaceOrderResult sell(BigDecimal amount, BigDecimal price) throws IOException {

    synchronized (session) {
      return btcTrade.sell(amount.toPlainString(), price.toPlainString(), nextNonce(), publicKey, getSignatureCreator());
    }
  }

}
