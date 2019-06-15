package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.currency.CustomCurrencyPairSerializer;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class CoindealTradeServiceRaw extends CoindealBaseService {

  public CoindealTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CoindealTradeHistory> getCoindealTradeHistory(TradeHistoryParamsAll params) throws IOException,CoindealException{
    return coindeal.getTradeHistory(
            basicAuthentication,
            CoindealAdapters.adaptCurrencyPairToString(params.getCurrencyPair()),
            params.getLimit());
  }

  public List<CoindealOrder> getCoindealOpenOrders(CurrencyPair currencyPair)throws IOException,CoindealException{
        return coindeal.getActiveOrders(
                basicAuthentication,
                CoindealAdapters.adaptCurrencyPairToString(currencyPair));

  }

  public CoindealOrder placeCoindealOrder(LimitOrder limitOrder) throws IOException,CoindealException {
      return coindeal.placeOrder(
          basicAuthentication,
          CoindealAdapters.adaptCurrencyPairToString(limitOrder.getCurrencyPair()),
          CoindealAdapters.adaptOrderType(limitOrder.getType()),
          "limit",
          "GTC",
          limitOrder.getOriginalAmount().doubleValue(),
          limitOrder.getLimitPrice().doubleValue());
  }

  public List<CoindealOrder> deleteCoindealOrders(CurrencyPair currencyPair) throws IOException,CoindealException{
        return coindeal.deleteOrders(
            basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair));
  }

  public CoindealOrder deleteCoindealOrderById(String orderId) throws IOException,CoindealException{
      return coindeal.deleteOrderById(basicAuthentication, orderId);
  }
}
