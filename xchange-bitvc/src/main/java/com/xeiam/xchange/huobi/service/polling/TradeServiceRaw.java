package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;
import com.xeiam.xchange.dto.Order.OrderType;

public interface TradeServiceRaw {

  public BitVcOrder[] getBitVcOrders(int coinType) throws IOException;

  public BitVcPlaceOrderResult placeBitVcLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException;

  public BitVcPlaceOrderResult placeBitVcMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException;

  public BitVcCancelOrderResult cancelBitVcOrder(int coinType, long id) throws IOException;

}