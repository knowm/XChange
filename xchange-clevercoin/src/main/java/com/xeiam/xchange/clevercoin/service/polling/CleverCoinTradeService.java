package com.xeiam.xchange.clevercoin.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.CleverCoinAdapters;
import com.xeiam.xchange.clevercoin.dto.CleverCoinException;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOpenOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

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

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, CleverCoinException {

    int numberOfTransactions = 100;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("Argument must be a Number!");
      } else {
        numberOfTransactions = ((Number) args[0]).intValue();
      }
    }

    TradeHistoryParamPaging params = createTradeHistoryParams();
    params.setPageLength(numberOfTransactions);
    return getTradeHistory(params);
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

}
