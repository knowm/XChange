package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonPropertyOrder({
    "symbol",
    "bid",
    "bidSize",
    "ask",
    "askSize",
    "dailyChange",
    "dailyChangePerc",
    "lastPrice",
    "volume",
    "high",
    "low"
})
public class BitfinexTickerTraidingPair implements BitfinexTicker {

  private String symbol;
  private BigDecimal bid;
  private BigDecimal bidSize;
  private BigDecimal ask;
  private BigDecimal askSize;
  private BigDecimal dailyChange;
  private BigDecimal dailyChangePerc;
  private BigDecimal lastPrice;
  private BigDecimal volume;
  private BigDecimal high;
  private BigDecimal low;

  @Override
  public boolean isTradingPair() {
    return true;
  }
}
