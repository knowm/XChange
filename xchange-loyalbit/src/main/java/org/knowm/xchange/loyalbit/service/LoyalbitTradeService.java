package org.knowm.xchange.loyalbit.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.loyalbit.LoyalbitAdapters;
import org.knowm.xchange.loyalbit.LoyalbitAuthenticated;
import org.knowm.xchange.loyalbit.dto.LoyalbitException;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitOrder;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPagingSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Matija Mazi
 */
public class LoyalbitTradeService extends LoyalbitTradeServiceRaw implements TradeService {

  public LoyalbitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, LoyalbitException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    LoyalbitOrder[] openOrders = getLoyalbitOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (LoyalbitOrder loyalbitOrder : openOrders) {
      limitOrders.add(LoyalbitAdapters.adaptOrder(loyalbitOrder));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, LoyalbitException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, LoyalbitException {
    final LoyalbitOrder.Type type = limitOrder.getType() == BID ? LoyalbitOrder.Type.bid : LoyalbitOrder.Type.ask;
    LoyalbitSubmitOrderResponse loyalbitOrder = placeLoyalbitOrder(type, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    return Long.toString(loyalbitOrder.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, LoyalbitException {
    cancelLoyalbitOrder(Long.parseLong(orderId));
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer offset = 0;
    Integer limit = 100;
    LoyalbitAuthenticated.Sort sort = LoyalbitAuthenticated.Sort.desc;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
      offset = paging.getPageLength() * paging.getPageNumber();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder() == TradeHistoryParamsSorted.Order.asc ? LoyalbitAuthenticated.Sort.asc
          : LoyalbitAuthenticated.Sort.desc;
    }
    return LoyalbitAdapters.adaptTradeHistory(getLoyalbitUserTransactions(offset, limit, sort));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPagingSorted(100, TradeHistoryParamsSorted.Order.desc);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
