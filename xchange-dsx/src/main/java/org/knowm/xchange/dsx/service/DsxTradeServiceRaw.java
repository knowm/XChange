package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxBalance;
import org.knowm.xchange.dsx.dto.DsxMarketOrder;
import org.knowm.xchange.dsx.dto.DsxOrder;
import org.knowm.xchange.dsx.dto.DsxOwnTrade;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class DsxTradeServiceRaw extends DsxBaseService {

  public DsxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<DsxOrder> getOpenOrdersRaw() throws IOException {
    return dsx.getDsxActiveOrders();
  }

  public DsxOrder placeMarketOrderRaw(MarketOrder marketOrder) throws IOException {

    String symbol = DsxAdapters.adaptCurrencyPair(marketOrder.getCurrencyPair());
    String side = DsxAdapters.getSide(marketOrder.getType()).toString();

    String clientOrderId = null;
    if (marketOrder instanceof DsxMarketOrder) {
      clientOrderId = ((DsxMarketOrder) marketOrder).getClientOrderId();
    }

    return dsx.postDsxNewOrder(
        clientOrderId,
        symbol,
        side,
        null,
        marketOrder.getOriginalAmount(),
        DsxOrderType.market,
        DsxTimeInForce.IOC);
  }

  public DsxOrder placeLimitOrderRaw(LimitOrder limitOrder, DsxTimeInForce timeInForce)
      throws IOException {
    String symbol = DsxAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    String side = DsxAdapters.getSide(limitOrder.getType()).toString();

    return dsx.postDsxNewOrder(
        limitOrder.getUserReference(),
        symbol,
        side,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount(),
        DsxOrderType.limit,
        timeInForce);
  }

  public DsxOrder placeLimitOrderRaw(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderRaw(limitOrder, DsxTimeInForce.GTC);
  }

  public DsxOrder updateMarketOrderRaw(
      String clientOrderId, BigDecimal quantity, String requestClientId, Optional<BigDecimal> price)
      throws IOException {

    return dsx.updateDsxOrder(clientOrderId, quantity, requestClientId, price.orElse(null));
  }

  public DsxOrder cancelOrderRaw(String clientOrderId) throws IOException {
    return dsx.cancelSingleOrder(clientOrderId);
  }

  public List<DsxOrder> cancelAllOrdersRaw(String symbol) throws IOException {
    return dsx.cancelAllOrders(symbol);
  }

  public List<DsxOwnTrade> getHistorialTradesByOrder(String orderId) throws IOException {
    return dsx.getHistorialTradesByOrder(orderId);
  }

  public List<DsxOrder> getDsxRecentOrders() throws IOException {
    return dsx.getDsxRecentOrders();
  }

  public List<DsxOwnTrade> getTradeHistoryRaw(String symbol, Integer limit, long offset)
      throws IOException {
    return dsx.getDsxTrades(symbol, null, null, null, null, limit, offset);
  }

  public List<DsxOwnTrade> getTradeHistoryRaw(
      String symbol, DsxSort sort, Date from, Date till, Integer limit, long offset)
      throws IOException {
    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = from != null ? Instant.ofEpochMilli(from.getTime()).toString() : null;
    String tillValue = till != null ? Instant.ofEpochMilli(till.getTime()).toString() : null;
    return dsx.getDsxTrades(symbol, sortValue, "timestamp", fromValue, tillValue, limit, offset);
  }

  public List<DsxOwnTrade> getTradeHistoryRaw(
      String symbol, DsxSort sort, Long fromId, Long tillId, Integer limit, long offset)
      throws IOException {
    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = fromId != null ? fromId.toString() : null;
    String tillValue = tillId != null ? tillId.toString() : null;
    return dsx.getDsxTrades(symbol, sortValue, "id", fromValue, tillValue, limit, offset);
  }

  public DsxOrder getDsxOrder(String symbol, String clientOrderId) throws IOException {
    List<DsxOrder> orders = dsx.getDsxOrder(symbol, clientOrderId);

    if (orders == null || orders.isEmpty()) {
      return null;
    } else {
      return orders.iterator().next();
    }
  }

  public List<DsxBalance> getTradingBalance() throws IOException {

    return dsx.getTradingBalance();
  }
}
