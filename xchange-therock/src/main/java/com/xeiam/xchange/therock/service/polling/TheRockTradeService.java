package com.xeiam.xchange.therock.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.therock.TheRockAdapters;
import com.xeiam.xchange.therock.dto.TheRockException;
import com.xeiam.xchange.therock.dto.trade.TheRockOrder;

/**
 * @author Matija Mazi
 */
public class TheRockTradeService extends TheRockTradeServiceRaw implements PollingTradeService {

  public TheRockTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, TheRockException {
    final TheRockOrder placedOrder = placeOrder(order.getCurrencyPair(), order.getTradableAmount(), null, TheRockAdapters.adaptSide(order.getType()),
        TheRockOrder.Type.market);
    return placedOrder.getId().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, TheRockException {
    final TheRockOrder placedOrder = placeOrder(order.getCurrencyPair(), order.getTradableAmount(), order.getLimitPrice(),
        TheRockAdapters.adaptSide(order.getType()), TheRockOrder.Type.limit);
    return placedOrder.getId().toString();
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, TheRockException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, TheRockException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, TheRockException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }
}
