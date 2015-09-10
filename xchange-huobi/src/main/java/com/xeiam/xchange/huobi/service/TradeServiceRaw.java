package com.xeiam.xchange.huobi.service;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import com.xeiam.xchange.huobi.dto.trade.HuobiOrder;
import com.xeiam.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

public interface TradeServiceRaw {

  public HuobiOrder[] getOrders(int coinType) throws IOException;

  public HuobiPlaceOrderResult placeLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException;

  public HuobiPlaceOrderResult placeMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException;

  public HuobiCancelOrderResult cancelOrder(int coinType, long id) throws IOException;

}