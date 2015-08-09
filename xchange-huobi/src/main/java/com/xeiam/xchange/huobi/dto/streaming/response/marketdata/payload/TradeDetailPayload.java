package com.xeiam.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.TradeDetail;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.Orders;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqTradeDetailTopPayload;

/**
 * Payload of {@link TradeDetail}.
 */
public class TradeDetailPayload extends ReqTradeDetailTopPayload implements com.xeiam.xchange.huobi.dto.streaming.dto.TradeDetail {

  public TradeDetailPayload(String symbolId, long[] tradeId, BigDecimal[] price, long[] time, BigDecimal[] amount, int[] direction, Orders[] topAsks,
      Orders[] topBids) {
    super(symbolId, tradeId, price, time, amount, direction, topAsks, topBids);
  }

}
