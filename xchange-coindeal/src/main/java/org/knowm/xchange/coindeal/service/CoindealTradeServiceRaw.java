package org.knowm.xchange.coindeal.service;

import java.io.IOException;
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

  public List<CoindealTradeHistory> getCoindealTradeHistory(TradeHistoryParamsAll params) throws IOException{
//    List<CoindealTradeHistory> tradeHistoryList = new ArrayList<>();
    return coindeal.getTradeHistory(
            basicAuthentication,
            CoindealAdapters.adaptCurrencyPairToString(params.getCurrencyPair()),
            params.getLimit());
//    for (CoindealTradeHistory tradeHistory1 : tradeHistory) {
//      tradeHistoryList.add(tradeHistory1);
//    }
//    return tradeHistoryList;
  }

  public CoindealOrder placeCoindealOrder(LimitOrder limitOrder) throws IOException {
    try {
      return coindeal.placeOrder(
          basicAuthentication,
          CoindealAdapters.adaptCurrencyPairToString(limitOrder.getCurrencyPair()),
          CoindealAdapters.adaptOrderType(limitOrder.getType()),
          "limit",
          "GTC",
          limitOrder.getOriginalAmount().doubleValue(),
          limitOrder.getLimitPrice().doubleValue());
    } catch (CoindealException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<CoindealOrder> deleteCoindealOrders(CurrencyPair currencyPair) throws IOException{
    try{
        return coindeal.deleteOrders(
            basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair));
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  public CoindealOrder deleteCoindealOrderById(String orderId) throws IOException{
    try{
      return coindeal.deleteOrderById(basicAuthentication, orderId);
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }
}
