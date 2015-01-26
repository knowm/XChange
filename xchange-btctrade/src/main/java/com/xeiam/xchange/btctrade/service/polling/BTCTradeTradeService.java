package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class BTCTradeTradeService extends BTCTradeTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return BTCTradeAdapters.adaptOpenOrders(getBTCTradeOrders(0, "open"));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    final BTCTradePlaceOrderResult result;
    if (limitOrder.getType() == OrderType.BID) {
      result = buy(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    } else {
      result = sell(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    return BTCTradeAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCTradeResult result = cancelBTCTradeOrder(orderId);
    return BTCTradeAdapters.adaptResult(result);
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    long since = arguments.length > 0 ? toLong(arguments[0]) : 0L;

    BTCTradeOrder[] orders = getBTCTradeOrders(since, "all");
    BTCTradeOrder[] orderDetails = new BTCTradeOrder[orders.length];

    for (int i = 0; i < orders.length; i++) {
      orderDetails[i] = getBTCTradeOrder(orders[i].getId());
    }

    return BTCTradeAdapters.adaptTrades(orders, orderDetails);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws NotYetImplementedForExchangeException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    throw new NotYetImplementedForExchangeException();
  }

}
