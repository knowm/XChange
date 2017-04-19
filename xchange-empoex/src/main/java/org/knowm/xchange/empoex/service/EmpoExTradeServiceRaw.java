package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.empoex.EmpoExErrorException;
import org.knowm.xchange.empoex.EmpoExException;
import org.knowm.xchange.empoex.EmpoExUtils;
import org.knowm.xchange.empoex.dto.trade.EmpoExOpenOrder;
import org.knowm.xchange.empoex.dto.trade.EmpoExOrderResponse;
import org.knowm.xchange.exceptions.ExchangeException;

public class EmpoExTradeServiceRaw extends EmpoExBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, List<EmpoExOpenOrder>> getEmpoExOpenOrders() throws IOException {

    try {
      return empoExAuthenticated.getEmpoExOpenOrders(apiKey);
    } catch (EmpoExErrorException e) {

      if (e.getError().equals("No open orders")) {
        return new HashMap<>();
      } else {
        throw new ExchangeException(e.getError(), e);
      }
    }
  }

  public boolean cancel(String orderId) throws IOException {

    try {
      Map<String, Boolean> response = empoExAuthenticated.cancelEmpoExOrder(apiKey, orderId);
      return response.get("success");
    } catch (EmpoExException e) {
      throw new ExchangeException(e);
    }
  }

  public String buy(LimitOrder limitOrder) throws IOException {

    try {
      EmpoExOrderResponse response = empoExAuthenticated.buy(apiKey, EmpoExUtils.toPairString(limitOrder.getCurrencyPair()),
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());
      if (response.getSuccess()) {
        return response.getOrderId();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    } catch (EmpoExException e) {
      throw new ExchangeException(e);
    }
  }

  public String sell(LimitOrder limitOrder) throws IOException {

    try {
      EmpoExOrderResponse response = empoExAuthenticated.sell(apiKey, EmpoExUtils.toPairString(limitOrder.getCurrencyPair()),
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());
      if (response.getSuccess()) {
        return response.getOrderId();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    } catch (EmpoExException e) {
      throw new ExchangeException(e);
    }
  }

}
