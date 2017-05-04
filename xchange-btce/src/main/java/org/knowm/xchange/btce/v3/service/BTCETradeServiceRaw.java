package org.knowm.xchange.btce.v3.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAuthenticated;
import org.knowm.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrder;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrderInfoResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrderInfoReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryReturn;

/**
 * Author: brox Since: 2014-02-13
 */

public class BTCETradeServiceRaw extends BTCEBaseService {

  private static final String MSG_NO_TRADES = "no trades";
  private static final String MSG_BAD_STATUS = "bad status";

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCETradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @param pair The pair to display the orders e.g. btc_usd (null: all pairs)
   * @return Active orders map
   * @throws IOException
   */
  public Map<Long, BTCEOrder> getBTCEActiveOrders(String pair) throws IOException {

    BTCEOpenOrdersReturn orders = btce.ActiveOrders(apiKey, signatureCreator, exchange.getNonceFactory(), pair);
    if ("no orders".equals(orders.getError())) {
      return new HashMap<>();
    }
    checkResult(orders);
    return orders.getReturnValue();
  }

  /**
   * @param order BTCEOrder object
   * @return BTCEPlaceOrderResult object
   * @throws IOException
   */
  public BTCEPlaceOrderResult placeBTCEOrder(BTCEOrder order) throws IOException {

    String pair = order.getPair().toLowerCase();
    BTCEPlaceOrderReturn ret = btce.Trade(apiKey, signatureCreator, exchange.getNonceFactory(), pair, order.getType(), order.getRate(),
        order.getAmount());
    checkResult(ret);
    return ret.getReturnValue();
  }

  public BTCECancelOrderResult cancelBTCEOrder(long orderId) throws IOException {

    BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
    if (MSG_BAD_STATUS.equals(ret.getError())) {
      return null;
    }

    checkResult(ret);
    return ret.getReturnValue();
  }

  /**
   * All parameters are nullable
   *
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @param pair The pair to show the transaction; example btc_usd; all pairs
   * @return {success=1, return={tradeId={pair=btc_usd, type=sell, amount=1, rate=1, orderId=1234, timestamp=1234}}}
   */
  public Map<Long, BTCETradeHistoryResult> getBTCETradeHistory(Long from, Long count, Long fromId, Long endId, BTCEAuthenticated.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    BTCETradeHistoryReturn btceTradeHistory = btce.TradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), from, count, fromId, endId,
        order, since, end, pair);
    String error = btceTradeHistory.getError();
    // BTC-e returns this error if it finds no trades matching the criteria
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(btceTradeHistory);
    return btceTradeHistory.getReturnValue();
  }

  /**
   * Get Map of transaction history from BTCE exchange. All parameters are nullable
   *
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @return Map of transaction id's to transaction history results.
   */
  public Map<Long, BTCETransHistoryResult> getBTCETransHistory(Long from, Long count, Long fromId, Long endId, BTCEAuthenticated.SortOrder order,
      Long since, Long end) throws IOException {

    BTCETransHistoryReturn btceTransHistory = btce.TransHistory(apiKey, signatureCreator, exchange.getNonceFactory(), from, count, fromId, endId,
        order, since, end);
    String error = btceTransHistory.getError();
    // BTC-e returns this error if it finds no trades matching the criteria
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(btceTransHistory);
    return btceTransHistory.getReturnValue();
  }

  /**
   * Get order info from BTCE exchange.
   *
   * @param orderId The ID of the order to display
   * @return Order info.
   */
  public BTCEOrderInfoResult getBTCEOrderInfo(Long orderId) throws IOException {

    BTCEOrderInfoReturn btceOrderInfo = btce.OrderInfo(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
    
    checkResult(btceOrderInfo);
    
    return btceOrderInfo.getReturnValue().values().iterator().next();
  }
}
