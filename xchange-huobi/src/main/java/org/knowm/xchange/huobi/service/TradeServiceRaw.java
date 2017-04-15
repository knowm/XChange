package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderInfo;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

public interface TradeServiceRaw {

  HuobiOrder[] getOrders(int coinType) throws IOException;

  HuobiOrderInfo getOrderInfo(long orderId, int coinType) throws IOException;

  HuobiPlaceOrderResult placeLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException;

  HuobiPlaceOrderResult placeMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException;

  HuobiCancelOrderResult cancelOrder(int coinType, long id) throws IOException;

}