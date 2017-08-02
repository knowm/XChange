package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.itbit.v1.ItBitAdapters;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class ItBitTradeService extends ItBitTradeServiceRaw implements TradeService {

  public ItBitTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CurrencyPair currencyPair = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    // In case of no currency pair - return all currency pairs.
    if (currencyPair == null) {
      List<ItBitOrder> orders = new ArrayList<>();
      for (CurrencyPair tmpCurrencyPair : this.exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
        orders.addAll(Arrays.asList(getItBitOpenOrders(tmpCurrencyPair)));
      }
      ItBitOrder[] empty = {};
      return ItBitAdapters.adaptPrivateOrders(orders.isEmpty() ? empty : Arrays.copyOf(orders.toArray(), orders.size(), ItBitOrder[].class));
    } else {
      ItBitOrder[] itBitOpenOrders = getItBitOpenOrders(currencyPair);
      return ItBitAdapters.adaptPrivateOrders(itBitOpenOrders);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return placeItBitLimitOrder(limitOrder).getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    cancelItBitOrder(orderId);
    return true;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer page = ((TradeHistoryParamPaging) params).getPageNumber();
    if (page != null) {
      ++page;
    }

    ItBitTradeHistory userTradeHistory = getUserTradeHistory(((TradeHistoryParamTransactionId) params).getTransactionId(), page,
        ((TradeHistoryParamPaging) params).getPageLength(), ((TradeHistoryParamsTimeSpan) params).getStartTime(),
        ((TradeHistoryParamsTimeSpan) params).getEndTime());
    return ItBitAdapters.adaptTradeHistory(userTradeHistory);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new ItBitTradeHistoryParams(50, 0, null, null, null);
  }

  @Override
  public ItBitOpenOrdersParams createOpenOrdersParams() {
    return new ItBitOpenOrdersParams();
  }

  public static class ItBitTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamsTimeSpan, TradeHistoryParamTransactionId, TradeHistoryParamPaging {

    private String txId;
    private Date startTime;
    private Date endTime;

    public ItBitTradeHistoryParams(Integer pageLength, Integer pageNumber, String txId, Date startTime, Date endTime) {
      super(pageLength, pageNumber);
      this.txId = txId;
      this.startTime = startTime;
      this.endTime = endTime;
    }

    @Override
    public void setTransactionId(String txId) {
      this.txId = txId;
    }

    @Override
    public String getTransactionId() {
      return txId;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
