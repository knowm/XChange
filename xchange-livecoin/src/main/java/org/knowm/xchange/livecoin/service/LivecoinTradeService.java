package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class LivecoinTradeService extends LivecoinTradeServiceRaw implements TradeService {
  public LivecoinTradeService(LivecoinExchange livecoinExchange) {
    super(livecoinExchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return makeMarketOrder(marketOrder);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return makeLimitOrder(limitOrder);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
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

    return new UserTrades(tradeHistory(start, end, limit, offset), Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CurrencyPair currencyPair = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    if (currencyPair == null)
      throw new IllegalStateException("Currency pair needed");


    Date start = new Date(0);
    Date end = new Date();
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      start = tradeHistoryParamsTimeSpan.getStartTime();
      end = tradeHistoryParamsTimeSpan.getEndTime();
    }

    Long startRow = 0L;
    Long endRow = 100L;

    return new OpenOrders(getOpenOrders(currencyPair, start, end, startRow, endRow));
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
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
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }
}
