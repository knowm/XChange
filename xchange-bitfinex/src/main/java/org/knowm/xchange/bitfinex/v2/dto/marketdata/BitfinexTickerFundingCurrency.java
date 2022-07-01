package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BitfinexTickerFundingCurrency implements BitfinexTicker {

  private String symbol;
  private BigDecimal frr;
  private BigDecimal bid;
  private BigDecimal bidPeriod;
  private BigDecimal bidSize;
  private BigDecimal ask;
  private BigDecimal askPeriod;
  private BigDecimal askSize;
  private BigDecimal dailyChange;
  private BigDecimal dailyChangePerc;
  private BigDecimal lastPrice;
  private BigDecimal volume;
  private BigDecimal high;
  private BigDecimal low;
  private Object placeHolder0;
  private Object placeHolder1;
  private BigDecimal frrAmountAvailable;

  @Override
  public boolean isFundingCurrency() {
    return true;
  }
}
