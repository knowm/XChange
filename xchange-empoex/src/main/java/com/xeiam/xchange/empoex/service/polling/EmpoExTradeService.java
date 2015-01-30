package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.empoex.EmpoExAdapters;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class EmpoExTradeService extends EmpoExTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return EmpoExAdapters.adaptOpenOrders(super.getEmpoExOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    if (limitOrder.getType() == OrderType.BID) {
      return super.buy(limitOrder);
    } else {
      return super.sell(limitOrder);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancel(orderId);
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotYetImplementedForExchangeException();
  }

}
