package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.TradeDetail;
import org.knowm.xchange.huobi.dto.streaming.response.payload.Orders;
import org.knowm.xchange.huobi.dto.streaming.response.payload.ReqTradeDetailTopPayload;

/**
 * Payload of {@link TradeDetail}.
 */
public class TradeDetailPayload extends ReqTradeDetailTopPayload implements org.knowm.xchange.huobi.dto.streaming.dto.TradeDetail {

  public TradeDetailPayload(String symbolId, long[] tradeId, BigDecimal[] price, long[] time, BigDecimal[] amount, int[] direction, Orders[] topAsks,
      Orders[] topBids) {
    super(symbolId, tradeId, price, time, amount, direction, topAsks, topBids);
  }

}
