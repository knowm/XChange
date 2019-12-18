package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class CoindealTradeServiceRaw extends CoindealBaseService {

  public CoindealTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CoindealTradeHistory> getTradeHistory(TradeHistoryParamsAll params)
      throws IOException {
    return coindeal.getTradeHistory(
        basicAuthentication,
        CoindealAdapters.adaptCurrencyPairToString(params.getCurrencyPair()),
        params.getLimit());
  }

  public CoindealOrder placeOrder(LimitOrder limitOrder) throws IOException {
    return coindeal.placeOrder(
        basicAuthentication,
        CoindealAdapters.adaptCurrencyPairToString(limitOrder.getCurrencyPair()),
        CoindealAdapters.adaptOrderType(limitOrder.getType()),
        "limit",
        "GTC",
        limitOrder.getOriginalAmount().doubleValue(),
        limitOrder.getLimitPrice().doubleValue());
  }

  public List<CoindealOrder> deleteOrders(CurrencyPair currencyPair) throws IOException {
    return coindeal.deleteOrders(
        basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair));
  }

  public CoindealOrder deleteOrderById(String orderId) throws IOException {
    return coindeal.deleteOrderById(basicAuthentication, orderId);
  }
}
