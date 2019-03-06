package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class CoindealTradeServiceRaw extends CoindealBaseService {

  public CoindealTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CoindealTradeHistory> getTradeHistory(TradeHistoryParamsAll params) {
    List<CoindealTradeHistory> tradeHistoryList = new ArrayList<>();
    CoindealTradeHistory[] tradeHistory =
        coindeal.getTradeHistory(
            basicAuthentication,
            CoindealAdapters.adaptCurrencyPair(params.getCurrencyPair()),
            params.getLimit());
    for (CoindealTradeHistory tradeHistory1 : tradeHistory) {
      tradeHistoryList.add(tradeHistory1);
    }
    return tradeHistoryList;
  }

  public CoindealOrder placeOrder(LimitOrder limitOrder) throws IOException {
    try {
      return coindeal.placeOrder(
          basicAuthentication,
          CoindealAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair()),
          CoindealAdapters.adaptOrderType(limitOrder.getType()),
          "limit",
          "GTC",
          limitOrder.getOriginalAmount().doubleValue(),
          limitOrder.getLimitPrice().doubleValue());
    } catch (CoindealException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public CoindealOrder[] deleteOrders(CurrencyPair currencyPair) {
    return coindeal.deleteOrders(
        basicAuthentication, CoindealAdapters.adaptCurrencyPair(currencyPair));
  }

  public CoindealOrder deleteOrderById(String orderId) {
    return coindeal.deleteOrderById(basicAuthentication, orderId);
  }
}
