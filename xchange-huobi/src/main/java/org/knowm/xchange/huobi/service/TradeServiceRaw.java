package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderInfo;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

public interface TradeServiceRaw {

  public HuobiOrder[] getOrders(int coinType) throws IOException;

  public HuobiOrderInfo getOrderInfo(long orderId, int coinType) throws IOException;

  public HuobiPlaceOrderResult placeLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException;

  public HuobiPlaceOrderResult placeMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException;

  public HuobiCancelOrderResult cancelOrder(int coinType, long id) throws IOException;

}