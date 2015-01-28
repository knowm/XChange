package com.xeiam.xchange.poloniex.service.polling;

/**
 * @author Zach Holmes
 */

import java.io.IOException;
import java.util.HashMap;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;
import com.xeiam.xchange.poloniex.PoloniexException;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexTradeResponse;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexUserTrade;

public class PoloniexTradeServiceRaw extends PoloniexBasePollingService<PoloniexAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexTradeServiceRaw(Exchange exchange) {

    super(PoloniexAuthenticated.class, exchange);
  }

  public HashMap<String, PoloniexOpenOrder[]> returnOpenOrders() throws IOException {

    return poloniex.returnOpenOrders(apiKey, signatureCreator, exchange.getNonceFactory(), "all");
  }

  public PoloniexUserTrade[] returnTradeHistory(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    return poloniex.returnTradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexUtils.toPairString(currencyPair), startTime,
        endTime);
  }

  public HashMap<String, PoloniexUserTrade[]> returnTradeHistory(Long startTime, Long endTime) throws IOException {

    String ignore = null; // only used so PoloniexAuthenticated.returnTradeHistory can be overloaded
    return poloniex.returnTradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), "all", startTime, endTime, ignore);
  }

  public PoloniexTradeResponse buy(LimitOrder limitOrder) throws IOException {

    try {
      PoloniexTradeResponse response = poloniex.buy(apiKey, signatureCreator, exchange.getNonceFactory(), limitOrder.getTradableAmount()
          .toPlainString(), limitOrder.getLimitPrice().toPlainString(), PoloniexUtils.toPairString(limitOrder.getCurrencyPair()));
      return response;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public PoloniexTradeResponse sell(LimitOrder limitOrder) throws IOException {

    try {
      PoloniexTradeResponse response = poloniex.sell(apiKey, signatureCreator, exchange.getNonceFactory(), limitOrder.getTradableAmount()
          .toPlainString(), limitOrder.getLimitPrice().toPlainString(), PoloniexUtils.toPairString(limitOrder.getCurrencyPair()));
      return response;
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError());
    }
  }

  public boolean cancel(String orderId) throws IOException {

    /*
     * Need to look up CurrencyPair associated with orderId Poloniex is working on fixing this
     */
    OpenOrders openOrders = PoloniexAdapters.adaptPoloniexOpenOrders(returnOpenOrders());
    for (LimitOrder order : openOrders.getOpenOrders()) {
      if (order.getId().equals(orderId)) {
        HashMap<String, String> response = poloniex.cancelOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId,
            PoloniexUtils.toPairString(order.getCurrencyPair()));
        if (response.containsKey("error")) {
          throw new ExchangeException(response.get("error"));
        } else {
          return response.get("success").toString().equals(new Integer(1).toString()) ? true : false;
        }
      }
    }

    throw new ExchangeException("Unable to find order #" + orderId);

  }

  public boolean cancel(String orderId, CurrencyPair currencyPair) throws IOException {

    /*
     * No need to look up CurrencyPair associated with orderId, as the caller will provide it.
     */
    HashMap<String, String> response = poloniex.cancelOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId,
        PoloniexUtils.toPairString(currencyPair));
    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    }
    return response.get("success").toString().equals(new Integer(1).toString()) ? true : false;
  }

}
