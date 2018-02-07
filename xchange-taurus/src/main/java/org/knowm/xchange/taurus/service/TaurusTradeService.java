package org.knowm.xchange.taurus.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.taurus.TaurusAdapters;
import org.knowm.xchange.taurus.dto.TaurusException;
import org.knowm.xchange.taurus.dto.trade.TaurusOrder;

/**
 * @author Matija Mazi
 */
public class TaurusTradeService extends TaurusTradeServiceRaw implements TradeService {

  public TaurusTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, TaurusException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    TaurusOrder[] openOrders = getTaurusOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (TaurusOrder taurusOrder : openOrders) {
      OrderType orderType = taurusOrder.getType();
      String id = taurusOrder.getId();
      BigDecimal price = taurusOrder.getPrice();
      limitOrders.add(new LimitOrder(orderType, taurusOrder.getAmount(), CurrencyPair.BTC_CAD, id, taurusOrder.getDatetime(), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, TaurusException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, TaurusException {
    TaurusOrder taurusOrder = limitOrder.getType() == BID ? buyTaurusOrder(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice())
        : sellTaurusOrder(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());

    return taurusOrder.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, TaurusException {
    return cancelTaurusOrder(orderId);
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
    Integer offset = null;
    Integer limit = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
      offset = paging.getPageLength() * paging.getPageNumber();
    }
    if (params instanceof TradeHistoryParamOffset) {
      final Long longOffset = ((TradeHistoryParamOffset) params).getOffset();
      offset = longOffset == null ? null : longOffset.intValue();
    }
    return TaurusAdapters.adaptTradeHistory(getTaurusUserTransactions(offset, limit, sort));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(1000);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
