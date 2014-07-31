package com.xeiam.xchange.btctrade.service.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;

public class BTCTradeTradeServiceRaw extends BTCTradeBaseTradePollingService {

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BTCTradeOrder[] getBTCTradeOrders(long since, String type) {

    synchronized (session) {
      return btcTrade.getOrders(since, type, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeOrder getBTCTradeOrder(String id) {

    synchronized (session) {
      return btcTrade.getOrder(id, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradeResult cancelBTCTradeOrder(String id) {

    synchronized (session) {
      return btcTrade.cancelOrder(id, nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradePlaceOrderResult buy(BigDecimal amount, BigDecimal price) {

    synchronized (session) {
      return btcTrade.buy(amount.toPlainString(), price.toPlainString(), nextNonce(), publicKey, getSignatureCreator());
    }
  }

  public BTCTradePlaceOrderResult sell(BigDecimal amount, BigDecimal price) {

    synchronized (session) {
      return btcTrade.sell(amount.toPlainString(), price.toPlainString(), nextNonce(), publicKey, getSignatureCreator());
    }
  }

}
