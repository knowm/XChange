package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.quoine.QuoineAdapters;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderResponse;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersList;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * @author Matija Mazi
 */
public class QuoineTradeService extends QuoineTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineTradeService(Exchange exchange, boolean useMargin) {
    super(exchange, useMargin);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    QuoineOrdersList quoineOrdersList = listQuoineOrders(null);
    return QuoineAdapters.adapteOpenOrders(quoineOrdersList);

  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeMarketOrder(marketOrder.getCurrencyPair(),
        marketOrder.getType() == OrderType.ASK ? "sell" : "buy", marketOrder.getTradableAmount());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeLimitOrder(limitOrder.getCurrencyPair(),
        limitOrder.getType() == OrderType.ASK ? "sell" : "buy", limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    QuoineOrderResponse quoineOrderResponse = cancelQuoineOrder(orderId);

    return quoineOrderResponse.getId() != null && (quoineOrderResponse.getId().equals(orderId));
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

}
