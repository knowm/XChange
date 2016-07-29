package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.MarketDetail;
import org.knowm.xchange.huobi.dto.streaming.response.payload.Orders;
import org.knowm.xchange.huobi.dto.streaming.response.payload.ReqMarketDetailPayload;
import org.knowm.xchange.huobi.dto.streaming.response.payload.Trades;

/**
 * Payload of {@link MarketDetail}.
 */
public class MarketDetailPayload extends ReqMarketDetailPayload {

  public MarketDetailPayload(String symbolId, BigDecimal priceNew, BigDecimal priceOpen, BigDecimal priceHigh, BigDecimal priceLow,
      BigDecimal priceLast, int level, BigDecimal amount, BigDecimal totalAmount, String amp, Trades trades, Orders bids, Orders asks,
      BigDecimal commissionRatio, BigDecimal poor, BigDecimal updownVolume, BigDecimal updownRatio, BigDecimal priceAverage, BigDecimal volumeRatio,
      BigDecimal turnVolume, BigDecimal turnoverRate, BigDecimal outerDisc, BigDecimal innerDisc, BigDecimal totalVolume) {
    super(symbolId, priceNew, priceOpen, priceHigh, priceLow, priceLast, level, amount, totalAmount, amp, trades, bids, asks, commissionRatio, poor,
        updownVolume, updownRatio, priceAverage, volumeRatio, turnVolume, turnoverRate, outerDisc, innerDisc, totalVolume);
  }

}
