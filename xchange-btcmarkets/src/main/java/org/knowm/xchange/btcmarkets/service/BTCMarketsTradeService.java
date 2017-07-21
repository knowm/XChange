package org.knowm.xchange.btcmarkets.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Matija Mazi
 */
public class BTCMarketsTradeService extends BTCMarketsTradeServiceRaw implements TradeService {

  private final CurrencyPair currencyPair;

  public BTCMarketsTradeService(Exchange exchange) {
    super(exchange);
    CurrencyPair cp = null;
    try {
      cp = (CurrencyPair) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(BTCMarketsExchange.CURRENCY_PAIR);
    } catch (ClassCastException e) {
      throw new IllegalArgumentException(e);
    }
    currencyPair = cp;
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), BigDecimal.ZERO, BTCMarketsOrder.Type.Market);
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), order.getLimitPrice(), BTCMarketsOrder.Type.Limit);
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
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    BTCMarketsOrders openOrders = getBTCMarketsOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair(), 50, null);

    return BTCMarketsAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BTCMarketsException {
    return cancelBTCMarketsOrder(Long.parseLong(orderId)).getSuccess();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit = null;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
    }
    Date since = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      since = ((TradeHistoryParamsTimeSpan) params).getStartTime();
    }
    CurrencyPair cp = this.currencyPair;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      final CurrencyPair paramsCp = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (paramsCp != null) {
        cp = paramsCp;
      }
    }
    final BTCMarketsTradeHistory response = getBTCMarketsUserTransactions(cp, limit, since);
    return BTCMarketsAdapters.adaptTradeHistory(response.getTrades(), cp);
  }

  @Override
  public HistoryParams createTradeHistoryParams() {
    return new HistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair(currencyPair);
  }

  public static class HistoryParams implements TradeHistoryParamPaging, TradeHistoryParamsTimeSpan, TradeHistoryParamCurrencyPair {
    private Integer limit = 100;
    private Date since;
    private CurrencyPair currencyPair;

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
    public void setStartTime(Date startTime) {
      since = startTime;
    }

    @Override
    public Date getStartTime() {
      return since;
    }

    @Override
    public void setEndTime(Date endTime) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Date getEndTime() {
      throw new UnsupportedOperationException();
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
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
