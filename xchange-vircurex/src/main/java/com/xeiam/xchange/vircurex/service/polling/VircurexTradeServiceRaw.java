package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.vircurex.VircurexAdapters;
import com.xeiam.xchange.vircurex.VircurexAuthenticated;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.trade.VircurexOpenOrdersReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;
import com.xeiam.xchange.vircurex.service.VircurexSha2Digest;

public class VircurexTradeServiceRaw extends VircurexBasePollingService {

  private VircurexAuthenticated vircurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    vircurex = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public String placeVircurexLimitOrder(LimitOrder limitOrder) throws IOException {

    String type = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
    String timestamp = VircurexUtils.getUtcTimestamp();
    String nonce = (System.currentTimeMillis() / 250L) + "";
    VircurexSha2Digest digest =
        new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "create_order", type.toString(), limitOrder.getTradableAmount().floatValue()
            + "", limitOrder.getCurrencyPair().counterSymbol.toLowerCase(), limitOrder.getLimitPrice().floatValue() + "", limitOrder.getCurrencyPair().baseSymbol.toLowerCase());

    VircurexPlaceOrderReturn ret =
        vircurex.trade(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, type.toString(), limitOrder.getTradableAmount().floatValue() + "",
            limitOrder.getCurrencyPair().counterSymbol.toLowerCase(), limitOrder.getLimitPrice().floatValue() + "", limitOrder.getCurrencyPair().baseSymbol.toLowerCase());

    timestamp = VircurexUtils.getUtcTimestamp();
    nonce = (System.currentTimeMillis() / 200L) + "";

    digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "release_order", ret.getOrderId());

    ret = vircurex.release(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, ret.getOrderId());
    return ret.getOrderId();
  }

  public OpenOrders getVircurexOpenOrders() throws IOException {

    String timestamp = VircurexUtils.getUtcTimestamp();
    String nonce = (System.currentTimeMillis() / 250L) + "";
    VircurexSha2Digest digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "read_orders");
    VircurexOpenOrdersReturn openOrdersReturn = vircurex.getOpenOrders(exchangeSpecification.getUserName(), nonce, digest.toString(), timestamp, VircurexUtils.RELEASED_ORDER);

    return new OpenOrders(VircurexAdapters.adaptOpenOrders(openOrdersReturn.getOpenOrders()));
  }

}
