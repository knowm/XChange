package org.knowm.xchange.btcmarkets.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Matija Mazi
 */
public class BTCMarketsTradeService extends BTCMarketsTradeServiceRaw implements TradeService {

  public BTCMarketsTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getOriginalAmount(), BigDecimal.ZERO, BTCMarketsOrder.Type.Market);
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getOriginalAmount(), order.getLimitPrice(), BTCMarketsOrder.Type.Limit);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  private String placeOrder(CurrencyPair currencyPair, Order.OrderType orderSide, BigDecimal amount, BigDecimal price,
      BTCMarketsOrder.Type orderType) throws IOException {
    BTCMarketsOrder.Side side = orderSide == BID ? BTCMarketsOrder.Side.Bid : BTCMarketsOrder.Side.Ask;
    final BTCMarketsPlaceOrderResponse orderResponse = placeBTCMarketsOrder(currencyPair, amount, price, side, orderType);
    return Long.toString(orderResponse.getId());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BTCMarketsException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    BTCMarketsOrders openOrders = getBTCMarketsOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair(), 50, null);

    return BTCMarketsAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BTCMarketsException {
    return cancelBTCMarketsOrder(Long.parseLong(orderId)).getSuccess();
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
    Integer limit = 200;
    if (params instanceof TradeHistoryParamPaging) {
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    Long since = 0L;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan tradeHistoryParamsIdSpan = (TradeHistoryParamsIdSpan) params;
      since = Long.valueOf(tradeHistoryParamsIdSpan.getStartId());
    }

    CurrencyPair cp = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair paramsCp = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (paramsCp != null) {
        cp = paramsCp;
      }
    }

    BTCMarketsTradeHistory response = getBTCMarketsUserTransactions(cp, limit, since);
    return BTCMarketsAdapters.adaptTradeHistory(response.getTrades(), cp);
  }

  @Override
  public HistoryParams createTradeHistoryParams() {
    return new HistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair(null);
  }

  public static class HistoryParams implements TradeHistoryParamPaging, TradeHistoryParamCurrencyPair, TradeHistoryParamsIdSpan {
    private Integer limit = 200;
    private CurrencyPair currencyPair;
    private String startId;

    @Override
    public Integer getPageLength() {
      return limit;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.limit = pageLength;
    }

    @Override
    public Integer getPageNumber() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setStartId(String startId) {
      this.startId = startId;
    }

    @Override
    public String getStartId() {
      return startId;
    }

    @Override
    public void setEndId(String endId) {

    }

    @Override
    public String getEndId() {
      return null;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
