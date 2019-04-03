package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinFutureComment {
  // 指数
  private final BigDecimal futureIndex;
  // 美元-人民币汇率
  private final BigDecimal rate;
  // 交割预估价  注意：交割预估价只有交割前三小时返回
  private final BigDecimal forecastPrice;
  // 合约最高限价
  private final BigDecimal high;
  // 合约最低限价
  private final BigDecimal low;

  public OkCoinFutureComment(
      @JsonProperty("future_index") final BigDecimal futureIndex,
      @JsonProperty("rate") final BigDecimal rate,
      @JsonProperty("forecast_price") final BigDecimal forecastPrice,
      @JsonProperty("high") final BigDecimal high,
      @JsonProperty("low") final BigDecimal low) {
    this.futureIndex = futureIndex;
    this.rate = rate;
    this.forecastPrice = forecastPrice;
    this.high = high;
    this.low = low;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getForecastPrice() {
    return forecastPrice;
  }

  public BigDecimal getFutureIndex() {
    return futureIndex;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }
}
