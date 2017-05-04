package org.known.xchange.dsx.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.known.xchange.dsx.DSXAuthenticated;
import org.known.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.known.xchange.dsx.dto.trade.DSXCancelOrderResult;
import org.known.xchange.dsx.dto.trade.DSXCancelOrderReturn;
import org.known.xchange.dsx.dto.trade.DSXOrder;
import org.known.xchange.dsx.dto.trade.DSXOrderHistoryResult;
import org.known.xchange.dsx.dto.trade.DSXOrderHistoryReturn;
import org.known.xchange.dsx.dto.trade.DSXTradeHistoryResult;
import org.known.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.known.xchange.dsx.dto.trade.DSXTradeResult;
import org.known.xchange.dsx.dto.trade.DSXTradeReturn;
import org.known.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.known.xchange.dsx.dto.trade.DSXTransHistoryReturn;

/**
 * @author Mikhail Wall
 */

public class DSXTradeServiceRaw extends DSXBaseService {

  private static final String MSG_NO_TRADES = "no trades";
  private static final String MSG_BAD_STATUS = "bad status";

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<Long, DSXOrder> getDSXActiveOrders(String pair) throws IOException {
    DSXActiveOrdersReturn orders = dsx.ActiveOrders(apiKey, signatureCreator, System.currentTimeMillis(), pair);
    if ("no orders".equals(orders.getError())) {
      return new HashMap<>();
    }
    checkResult(orders);
    return orders.getReturnValue();
  }

  public Map<Long, DSXOrder> getDSXActiveOrders() throws IOException {
    return getDSXActiveOrders("btcusd");
  }

  public DSXTradeResult tradeDSX(DSXOrder order) throws IOException {

    String pair = order.getPair().toLowerCase();
    DSXTradeReturn ret = dsx.Trade(apiKey, signatureCreator, System.currentTimeMillis(), order.getType(), order.getRate(),
        order.getAmount(), pair);
    checkResult(ret);
    return ret.getReturnValue();
  }

  public DSXCancelOrderResult cancelDSXOrder(long orderId) throws IOException {

    DSXCancelOrderReturn ret = dsx.CancelOrder(apiKey, signatureCreator, System.currentTimeMillis(), orderId);
    if (MSG_BAD_STATUS.equals(ret.getError())) {
      return null;
    }

    checkResult(ret);
    return ret.getReturnValue();
  }

  public Map<Long, DSXTradeHistoryResult> getDSXTradeHistory(Long from, Long count, Long fromId, Long endId, DSXAuthenticated.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    DSXTradeHistoryReturn dsxTradeHistory = dsx.TradeHistory(apiKey, signatureCreator, System.currentTimeMillis(), from, count, fromId, endId,
        order, since, end, pair);
    String error = dsxTradeHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxTradeHistory);
    return dsxTradeHistory.getReturnValue();
  }

  public Map<Long, DSXTransHistoryResult> getDSXTransHistory(Long from, Long count, Long fromId, Long endId, DSXAuthenticated.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    DSXTransHistoryReturn dsxTransHistory = dsx.TransHistory(apiKey, signatureCreator, System.currentTimeMillis(), from, count, fromId, endId,
        order, since, end);
    String error = dsxTransHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxTransHistory);
    return dsxTransHistory.getReturnValue();
  }

  public Map<Long, DSXOrderHistoryResult> getDSXOrderHistory(Long from, Long count, Long fromId, Long endId, DSXAuthenticated.SortOrder order,
      Long since, Long end, String pair) throws IOException {

    DSXOrderHistoryReturn dsxOrderHistory = dsx.OrderHistory(apiKey, signatureCreator, System.currentTimeMillis(), from, count, fromId,
        endId, order, since, end, pair);
    String error = dsxOrderHistory.getError();
    if (MSG_NO_TRADES.equals(error)) {
      return Collections.emptyMap();
    }

    checkResult(dsxOrderHistory);
    return dsxOrderHistory.getReturnValue();
  }
}
