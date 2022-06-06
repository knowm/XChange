package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.bittrex.BittrexErrorAdapter;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BittrexTradeService extends BittrexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeService(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bittrex, resilienceRegistries);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return placeBittrexLimitOrder(limitOrder);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      return placeBittrexMarketOrder(marketOrder);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      return new OpenOrders(BittrexAdapters.adaptOpenOrders(getBittrexOpenOrders(params)));
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      return BittrexConstants.CLOSED.equalsIgnoreCase(cancelBittrexLimitOrder(orderId).getStatus());
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
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

    CurrencyPair currencyPair = null;
    Date startDate = null;
    Date endDate = null;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamsTimeSpan) {
      Date start = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      if (start != null) {
        startDate = Date.from(start.toInstant().truncatedTo(ChronoUnit.SECONDS));
      }
      Date end = ((TradeHistoryParamsTimeSpan) params).getEndTime();
      if (end != null) {
        endDate = Date.from(end.toInstant().truncatedTo(ChronoUnit.SECONDS));
      }
    }

    try {
      List<BittrexOrder> tradeHistory =
          getBittrexUserTradeHistory(currencyPair, startDate, endDate);
      return new UserTrades(
          BittrexAdapters.adaptUserTrades(tradeHistory), Trades.TradeSortType.SortByTimestamp);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new BittrexTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    try {
      List<Order> orders = new ArrayList<>(orderQueryParams.length);
      for (OrderQueryParams params : orderQueryParams) {
        BittrexOrder order = getBittrexOrder(params.getOrderId());
        if (order != null) {
          LimitOrder limitOrder = BittrexAdapters.adaptOrder(order);
          orders.add(limitOrder);
        }
      }
      return orders;
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }
}
