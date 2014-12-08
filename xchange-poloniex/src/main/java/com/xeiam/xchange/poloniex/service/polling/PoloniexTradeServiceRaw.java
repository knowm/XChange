package com.xeiam.xchange.poloniex.service.polling;

/**
 * @author Zach Holmes
 */

import java.io.IOException;
import java.util.HashMap;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;
import com.xeiam.xchange.poloniex.PoloniexException;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexTradeResponse;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexUserTrade;

public class PoloniexTradeServiceRaw extends PoloniexBasePollingService<PoloniexAuthenticated> {

  public PoloniexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(PoloniexAuthenticated.class, exchangeSpecification);
  }

  public HashMap<String, PoloniexOpenOrder[]> returnOpenOrders() throws IOException {

    return poloniex.returnOpenOrders(apiKey, signatureCreator, String.valueOf(nextNonce()), "all");
  }

  public PoloniexUserTrade[] returnTradeHistory(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    return poloniex.returnTradeHistory(apiKey, signatureCreator, String.valueOf(nextNonce()), PoloniexUtils.toPairString(currencyPair), startTime, endTime);
  }

  public HashMap<String, PoloniexUserTrade[]> returnTradeHistory(Long startTime, Long endTime) throws IOException {

    String ignore = null; // only used so PoloniexAuthenticated.returnTradeHistory can be overloaded
    return poloniex.returnTradeHistory(apiKey, signatureCreator, String.valueOf(nextNonce()), "all", startTime, endTime, ignore);
  }

  public PoloniexTradeResponse buy(LimitOrder limitOrder) throws IOException {

    try {
      PoloniexTradeResponse response =
          poloniex.buy(apiKey, signatureCreator, String.valueOf(nextNonce()), limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString(), PoloniexUtils
              .toPairString(limitOrder.getCurrencyPair()));
      return response;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexTradeResponse sell(LimitOrder limitOrder) throws IOException {

    try {
      PoloniexTradeResponse response =
          poloniex.sell(apiKey, signatureCreator, String.valueOf(nextNonce()), limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString(), PoloniexUtils
              .toPairString(limitOrder.getCurrencyPair()));
      return response;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public boolean cancel(String orderId) throws IOException {

    /*
     * Need to look up CurrencyPair associated with orderId
     * Poloniex is working on fixing this
     */
    OpenOrders openOrders = PoloniexAdapters.adaptPoloniexOpenOrders(returnOpenOrders());
    for (LimitOrder order : openOrders.getOpenOrders()) {
      if (order.getId().equals(orderId)) {
        HashMap<String, String> response = poloniex.cancelOrder(apiKey, signatureCreator, String.valueOf(nextNonce()), orderId, PoloniexUtils.toPairString(order.getCurrencyPair()));
        if (response.containsKey("error")) {
          throw new ExchangeException(response.get("error"));
        }
        else {
          return response.get("success").toString().equals(new Integer(1).toString()) ? true : false;
        }
      }
    }

    throw new ExchangeException("Unable to find order #" + orderId);

  }

}
