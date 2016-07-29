package org.knowm.xchange.huobi.dto.streaming.dto;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.payload.Orders;

public interface TradeDetail {

  String getSymbolId();

  long[] getTradeId();

  BigDecimal[] getPrice();

  long[] getTime();

  BigDecimal[] getAmount();

  int[] getDirection();

  Orders[] getTopAsks();

  Orders[] getTopBids();

}
