package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.knowm.xchange.dsx.dto.trade.DSXCancelAllOrdersResult;
import org.knowm.xchange.dsx.dto.trade.DSXCancelAllOrdersReturn;
import org.knowm.xchange.dsx.dto.trade.DSXCancelOrderResult;
import org.knowm.xchange.dsx.dto.trade.DSXCancelOrderReturn;
import org.knowm.xchange.dsx.dto.trade.DSXFeesResult;
import org.knowm.xchange.dsx.dto.trade.DSXFeesReturn;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXOrderHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXOrderHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXOrderStatusResult;
import org.knowm.xchange.dsx.dto.trade.DSXOrderStatusReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeResult;
import org.knowm.xchange.dsx.dto.trade.DSXTradeReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryReturn;

/**
 * @author Mikhail Wall
 */

public class DSXTradeServiceRaw extends DSXBaseService {

  private static final String MSG_NO_TRADES = "no trades";
  private static final String MSG_BAD_STATUS = "bad status";
  private static final String MSG_NO_ORDERS = "No open orders!";

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @param pair The pair to display the orders e.g. btcusd (null: all pairs)
   * @return Active orders map
   * @throws IOException
   */
  public Map<Long, DSXOrder> getDSXActiveOrders(String pair) throws IOException {
    DSXActiveOrdersReturn orders = dsx.getActiveOrders(apiKey, signatureCreator, exchange.getNonceFactory(), pair);
    if (MSG_NO_ORDERS.equals(orders.getError())) {
      return new HashMap<>();
    }
    checkResult(orders);
    return orders.getReturnValue();
  }

  /**
   * @param order DSXOrder object
   * @return DSXTradeResult object
   * @throws IOException
   */
  public DSXTradeResult tradeDSX(DSXOrder order) throws IOException {

    String pair = order.getPair().toLowerCase();
    DSXTradeReturn ret = dsx.Trade(apiKey, signatureCreator, exchange.getNonceFactory(), order.getType(), order.getRate(),
        order.getAmount(), pair, order.getOrderType());
    checkResult(ret);
    return ret.getReturnValue();
  }

  public DSXCancelOrderResult cancelDSXOrder(long orderId) throws IOException {

    DSXCancelOrderReturn ret = dsx.CancelOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
    if (MSG_BAD_STATUS.equals(ret.getError())) {
      return null;
    }

    checkResult(ret);
    return ret.getReturnValue();
  }

  public DSXCancelAllOrdersResult cancelAllDSXOrders() throws IOException {

    DSXCancelAllOrdersReturn ret = dsx.cancelAllOrders(apiKey, signatureCreator, exchange.getNonceFactory());
    if (MSG_BAD_STATUS.equals(ret.getError())) {
      return null;
    }

    checkResult(ret);
    return ret.getReturnValue();
  }

  public DSXOrderStatusResult getOrderStatus(Long orderId) throws IOException {
    DSXOrderStatusReturn ret = dsx.getOrderStatus(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
    if (MSG_BAD_STATUS.equals(ret.getError())) {
      return null;
    }

    checkResult(ret);
    return ret.getReturnValue();
  }

  public DSXFeesResult getFees() throws IOException {
    DSXFeesReturn res = dsx.getFees(apiKey, signatureCreator, exchange.getNonceFactory());
    if (MSG_BAD_STATUS.equals(res.getError())) {
      return null;
    }

    checkResult(res);
    return res.getReturnValue();
  }

  /**
   * Get Map of trade history from DSX exchange. All parameters are nullable
   *
   * @param count Number of trades to display
   * @param fromId ID of the first trade of the selection
   * @param endId ID of the last trade of the selection
   * @param order Order in which transactions shown. Possible values: «asc» — from first to last, «desc» — from last to first. Default value is «desc»
   * @param since Time from which start selecting trades by trade time(UNIX time). If this value is not null order will become «asc»
   * @param end 	Time to which start selecting trades by trade time(UNIX time). If this value is not null order will become «asc»
   * @param pair Currency pair
   * @return Map of trade history result
   * @throws IOException
   */
  public Map<Long, DSXTradeHistoryResult> getDSXTradeHistory(Long count, Long fromId, Long endId, DSXAuthenticatedV2.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    DSXTradeHistoryReturn dsxTradeHistory = dsx.TradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), count, fromId, endId,
        order, since, end, pair);
    String error = dsxTradeHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxTradeHistory);
    return dsxTradeHistory.getReturnValue();
  }

  /**
   * Get Map of transaction history from DSX exchange. All parameters are nullable
   *
   * @param count Number of transactions to display. Default value is 1000
   * @param fromId ID of the first transaction of the selection
   * @param endId ID of the last transaction of the selection
   * @param order Order in which transactions shown. Possible values: «asc» — from first to last, «desc» — from last to first. Default value is «desc»
   * @param since Time from which start selecting transaction by transaction time(UNIX time). If this value is not null order will become «asc»
   * @param end Time to which start selecting transaction by transaction time(UNIX time). If this value is not null order will become «asc»
   * @return Map of transaction history
   * @throws IOException
   */
  public Map<Long, DSXTransHistoryResult> getDSXTransHistory(Long count, Long fromId, Long endId, DSXAuthenticatedV2.SortOrder order,
      Long since, Long end, DSXTransHistoryResult.Type type, DSXTransHistoryResult.Status status, String currency) throws IOException {

    DSXTransHistoryReturn dsxTransHistory = dsx.TransHistory(apiKey, signatureCreator, exchange.getNonceFactory(), count, fromId, endId,
        order, since, end, type, status, currency);
    String error = dsxTransHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxTransHistory);
    return dsxTransHistory.getReturnValue();
  }

  /**
   * Get Map of order history from DSX exchange. All parameters are nullable
   * @param count Number of orders to display. Default value is 1000
   * @param fromId ID of the first order of the selection
   * @param endId ID of the last order of the selection
   * @param order Order in which transactions shown. Possible values: «asc» — from first to last, «desc» — from last to first. Default value is «desc»
   * @param since Time from which start selecting orders by trade time(UNIX time). If this value is not null order will become «asc»
   * @param end Time to which start selecting orders by trade time(UNIX time). If this value is not null order will become «asc»
   * @param pair Currency pair
   * @return Map of order history
   * @throws IOException
   */
  public Map<Long, DSXOrderHistoryResult> getDSXOrderHistory(Long count, Long fromId, Long endId, DSXAuthenticatedV2.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    DSXOrderHistoryReturn dsxOrderHistory = dsx.OrderHistory(apiKey, signatureCreator, exchange.getNonceFactory(), count, fromId,
        endId, order, since, end, pair);
    String error = dsxOrderHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxOrderHistory);
    return dsxOrderHistory.getReturnValue();
  }
}
