package org.knowm.xchange.yobit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.YoBitExchange;
import org.knowm.xchange.yobit.dto.BaseYoBitResponse;

public class YoBitTradeService extends YoBitTradeServiceRaw {
  public YoBitTradeService(YoBitExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      BaseYoBitResponse response = activeOrders((OpenOrdersParamCurrencyPair) params);

      List<LimitOrder> orders = new ArrayList<>();

      if (response.returnData != null) {
        for (Object key : response.returnData.keySet()) {
          Map tradeData = (Map) response.returnData.get(key);

          orders.add(YoBitAdapters.adaptOrder(key.toString(), tradeData));
        }
      }

      return new OpenOrders(orders);
    }

    throw new IllegalStateException("Need to specify currency pair");
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BaseYoBitResponse response = trade(limitOrder);

    return response.returnData.get("order_id").toString();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelOrderById(orderId).success;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer count = 1000;
    if (params instanceof TradeHistoryParamLimit) {
      count = ((TradeHistoryParamLimit) params).getLimit();
    }

    Long offset = 0L;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    String market = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      market = YoBitAdapters.adaptCcyPairToUrlFormat(currencyPair);
    }

    Long fromTransactionId = null;
    Long endTransactionId = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan tradeHistoryParamsIdSpan = (TradeHistoryParamsIdSpan) params;

      String startId = tradeHistoryParamsIdSpan.getStartId();
      if (startId != null)
        fromTransactionId = Long.valueOf(startId);

      String endId = tradeHistoryParamsIdSpan.getEndId();
      if (endId != null)
        endTransactionId = Long.valueOf(endId);
    }

    String order = "DESC";
    if (params instanceof TradeHistoryParamsSorted) {
      order = ((TradeHistoryParamsSorted) params).getOrder().equals(TradeHistoryParamsSorted.Order.desc) ? "DESC" : "ASC";
    }

    Long fromTimestamp = null;
    Long toTimestamp = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;

      Date startTime = tradeHistoryParamsTimeSpan.getStartTime();
      if (startTime != null)
        fromTimestamp = DateUtils.toUnixTimeNullSafe(startTime);

      Date endTime = tradeHistoryParamsTimeSpan.getEndTime();
      if (endTime != null)
        toTimestamp = DateUtils.toUnixTimeNullSafe(endTime);
    }

    BaseYoBitResponse response = tradeHistory(count, offset, market, fromTransactionId, endTransactionId, order, fromTimestamp, toTimestamp);

    List<UserTrade> trades = new ArrayList<>();

    if (response.returnData != null) {
      for (Object key : response.returnData.keySet()) {
        Map tradeData = (Map) response.returnData.get(key);
        trades.add(YoBitAdapters.adaptUserTrade(key, tradeData));
      }
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

}
