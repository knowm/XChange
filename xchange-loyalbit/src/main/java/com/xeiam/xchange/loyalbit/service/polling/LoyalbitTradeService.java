package com.xeiam.xchange.loyalbit.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.loyalbit.LoyalbitAdapters;
import com.xeiam.xchange.loyalbit.LoyalbitAuthenticated;
import com.xeiam.xchange.loyalbit.dto.LoyalbitException;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitOrder;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPagingSorted;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsSorted;

/**
 * @author Matija Mazi
 */
public class LoyalbitTradeService extends LoyalbitTradeServiceRaw implements PollingTradeService {

  public LoyalbitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, LoyalbitException {
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
  public UserTrades getTradeHistory(Object... args) throws IOException, LoyalbitException {
    return LoyalbitAdapters.adaptTradeHistory(getLoyalbitUserTransactions(0, 1000, LoyalbitAuthenticated.Sort.asc));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer offset = 0;
    Integer limit = 100;
    LoyalbitAuthenticated.Sort sort = LoyalbitAuthenticated.Sort.asc;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
      offset = paging.getPageLength() * paging.getPageNumber();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = LoyalbitAuthenticated.Sort.valueOf(((TradeHistoryParamsSorted) params).getOrder().name());
    }
    return LoyalbitAdapters.adaptTradeHistory(getLoyalbitUserTransactions(offset, limit, sort));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPagingSorted(100);
  }
}
