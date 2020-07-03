package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BittrexTradeService extends BittrexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeService(BittrexExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeBittrexLimitOrder(limitOrder);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return new OpenOrders(BittrexAdapters.adaptOpenOrders(getBittrexOpenOrders(params)));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return BittrexConstants.CLOSED.equalsIgnoreCase(cancelBittrexLimitOrder(orderId).getStatus());
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    List<BittrexOrder> tradeHistory =
        (params instanceof TradeHistoryParamCurrencyPair)
            ? getBittrexUserTradeHistory(((TradeHistoryParamCurrencyPair) params).getCurrencyPair())
            : getBittrexUserTradeHistory();
    return new UserTrades(
        BittrexAdapters.adaptUserTrades(tradeHistory), Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      BittrexOrder order = getBittrexOrder(orderId);
      if (order != null) {
        LimitOrder limitOrder = BittrexAdapters.adaptOrder(order);
        orders.add(limitOrder);
      }
    }
    return orders;
  }
}
