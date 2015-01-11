package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.empoex.EmpoExAuthenticated;
import com.xeiam.xchange.empoex.EmpoExErrorException;
import com.xeiam.xchange.empoex.EmpoExException;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.trade.EmpoExOpenOrder;
import com.xeiam.xchange.empoex.dto.trade.EmpoExOrderResponse;

public class EmpoExTradeServiceRaw extends EmpoExBasePollingService<EmpoExAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public EmpoExTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(EmpoExAuthenticated.class, exchangeSpecification);
  }

  public Map<String, List<EmpoExOpenOrder>> getEmpoExOpenOrders() throws IOException {

    try {
      return empoex.getEmpoExOpenOrders(apiKey);
    } catch (EmpoExErrorException e) {

      if (e.getError().equals("No open orders")) {
        return new HashMap<String, List<EmpoExOpenOrder>>();
      } else {
        throw new ExchangeException(e.getError());
      }
    }
  }

  public boolean cancel(String orderId) throws IOException {

    try {
      Map<String, Boolean> response = empoex.cancelEmpoExOrder(apiKey, orderId);
      return response.get("success");
    } catch (EmpoExException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public String buy(LimitOrder limitOrder) throws IOException {

    try {
      EmpoExOrderResponse response = empoex.buy(apiKey, EmpoExUtils.toPairString(limitOrder.getCurrencyPair()), limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice()
          .toPlainString());
      if (response.getSuccess()) {
        return response.getOrderId();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    } catch (EmpoExException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public String sell(LimitOrder limitOrder) throws IOException {

    try {
      EmpoExOrderResponse response = empoex.sell(apiKey, EmpoExUtils.toPairString(limitOrder.getCurrencyPair()), limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice()
          .toPlainString());
      if (response.getSuccess()) {
        return response.getOrderId();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    } catch (EmpoExException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
