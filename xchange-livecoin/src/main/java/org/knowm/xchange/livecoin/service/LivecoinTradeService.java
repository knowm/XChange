package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.livecoin.LivecoinErrorAdapter;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class LivecoinTradeService extends LivecoinTradeServiceRaw implements TradeService {
  public LivecoinTradeService(LivecoinExchange livecoinExchange) {
    super(livecoinExchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      return makeMarketOrder(marketOrder);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return makeLimitOrder(limitOrder);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      Date start = new Date(0);
      Date end = new Date();
      if (params instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
        start = tradeHistoryParamsTimeSpan.getStartTime();
        end = tradeHistoryParamsTimeSpan.getEndTime();
      }

      Long offset = 0L;
      if (params instanceof TradeHistoryParamOffset) {
        offset = ((TradeHistoryParamOffset) params).getOffset();
      }

      Integer limit = 100;
      if (params instanceof TradeHistoryParamLimit) {
        limit = ((TradeHistoryParamLimit) params).getLimit();
      }

      return new UserTrades(
          tradeHistory(start, end, limit, offset), Trades.TradeSortType.SortByTimestamp);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      return new OpenOrders(getAllOpenOrders());
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
