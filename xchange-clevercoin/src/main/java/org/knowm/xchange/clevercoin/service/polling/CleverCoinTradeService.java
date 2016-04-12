package org.knowm.xchange.clevercoin.service.polling;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.clevercoin.CleverCoinAdapters;
import org.knowm.xchange.clevercoin.dto.CleverCoinException;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinOpenOrder;
import org.knowm.xchange.clevercoin.dto.trade.CleverCoinOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinTradeService extends CleverCoinTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, CleverCoinException {

    CleverCoinOrder[] openOrders = getCleverCoinOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (CleverCoinOrder cleverCoinOrder : openOrders) {
      OrderType orderType = cleverCoinOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = Integer.toString(cleverCoinOrder.getId());
      BigDecimal price = cleverCoinOrder.getPrice();
      limitOrders.add(new LimitOrder(orderType, cleverCoinOrder.getAmount(), CurrencyPair.BTC_EUR, id, cleverCoinOrder.getTime(), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, CleverCoinException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, CleverCoinException {

    CleverCoinOpenOrder cleverCoinOrder;
    String orderType = (limitOrder.getType() == BID ? "bid" : "ask");
    cleverCoinOrder = createCleverCoinOrder(orderType, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    if (cleverCoinOrder.getErrorMessage() != null) {
      throw new ExchangeException(cleverCoinOrder.getErrorMessage());
    }
    return cleverCoinOrder.getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, CleverCoinException {

    return cancelCleverCoinOrder(Integer.parseInt(orderId)).getResult().equals("success");
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer count = ((TradeHistoryParamPaging) params).getPageLength();
    return CleverCoinAdapters.adaptTradeHistory(getCleverCoinUserTransactions(count));
  }

  @Override
  public TradeHistoryParamPaging createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(100);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
