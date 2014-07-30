package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.justcoin.JustcoinAuthenticated;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.Utils;
import com.xeiam.xchange.justcoin.dto.trade.in.OrderReq;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;

/**
 * @author jamespedwards42
 */
public class JustcoinTradeServiceRaw extends JustcoinBasePollingService<JustcoinAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public JustcoinTradeServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(JustcoinAuthenticated.class, exchangeSpecification);
  }

  public JustcoinOrder[] getOrders() throws IOException {

    return justcoin.getOrders(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public JustcoinTrade[] getOrderHistory() throws IOException {

    return justcoin.getOrderHistory(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return justcoin.createMarketOrder(JustcoinUtils.getApiMarket(marketOrder.getCurrencyPair()), marketOrder.getType().toString().toLowerCase(), marketOrder.getTradableAmount(),
        getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    OrderReq req =
        new OrderReq(JustcoinUtils.getApiMarket(limitOrder.getCurrencyPair()), Utils.format(limitOrder.getLimitPrice()), Utils.format(limitOrder.getTradableAmount()), limitOrder.getType().toString()
            .toLowerCase());

    return justcoin.createLimitOrder(req, getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public boolean cancelOrder(String orderId) throws IOException {

    justcoin.cancelOrder(orderId, getBasicAuthentication(), exchangeSpecification.getApiKey());
    return true;
  }
}
