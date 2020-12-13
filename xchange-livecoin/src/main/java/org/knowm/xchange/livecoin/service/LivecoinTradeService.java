package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinErrorAdapter;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.livecoin.dto.LivecoinPaginatedResponse;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinUserOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class LivecoinTradeService extends LivecoinTradeServiceRaw implements TradeService {
  public LivecoinTradeService(
      LivecoinExchange livecoinExchange,
      Livecoin livecoin,
      ResilienceRegistries resilienceRegistries) {
    super(livecoinExchange, livecoin, resilienceRegistries);
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
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    try {
      if (!(params instanceof CancelOrderByCurrencyPair)
          && !(params instanceof CancelOrderByIdParams)) {
        throw new ExchangeException(
            "You need to provide the currency pair and the order id to cancel an order.");
      }
      CurrencyPair currencyPair = ((CancelOrderByCurrencyPair) params).getCurrencyPair();
      String orderId = ((CancelOrderByIdParams) params).getOrderId();
      return cancelOrder(currencyPair, orderId);
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
      CurrencyPair pair = null;
      if (params instanceof OpenOrdersParamCurrencyPair) {
        pair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      }
      LivecoinPaginatedResponse<LivecoinUserOrder> response =
          clientOrders(pair, "OPEN", null, null, null, null);
      if (response.getData() == null) {
        return new OpenOrders(Collections.emptyList());
      }
      return new OpenOrders(
          response.getData().stream()
              .filter(this::isOrderOpen)
              .map(LivecoinAdapters::adaptUserOrder)
              .filter(order -> order instanceof LimitOrder)
              .map(order -> (LimitOrder) order)
              .collect(Collectors.toList()));
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
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
    try {
      if (params == null || params.length == 0) {
        LivecoinPaginatedResponse<LivecoinUserOrder> response =
            clientOrders(null, null, null, null, null, null);
        return LivecoinAdapters.adaptUserOrders(response.getData());
      }
      List<Order> result = new ArrayList<>();
      for (OrderQueryParams param : params) {
        CurrencyPair pair = null;
        if (param instanceof OrderQueryParamCurrencyPair) {
          pair = ((OrderQueryParamCurrencyPair) param).getCurrencyPair();
        }
        LivecoinPaginatedResponse<LivecoinUserOrder> response =
            clientOrders(pair, null, null, null, null, null);
        if (param.getOrderId() == null) {
          result.addAll(LivecoinAdapters.adaptUserOrders(response.getData()));
        } else {
          response.getData().stream()
              .filter(order -> order.getId().toString().equals(param.getOrderId()))
              .findAny()
              .map(LivecoinAdapters::adaptUserOrder)
              .ifPresent(result::add);
        }
      }
      return result;
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }
}
