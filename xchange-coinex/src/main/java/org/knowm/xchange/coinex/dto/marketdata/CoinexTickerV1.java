package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CoinexTickerV1 {

  @JsonProperty("vol")
  private BigDecimal volume24h;

  @JsonProperty("low")
  private BigDecimal low24h;

  @JsonProperty("open")
  private BigDecimal open24h;

  @JsonProperty("high")
  private BigDecimal high24h;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("buy")
  private BigDecimal bestBidPrice;

  @JsonProperty("buy_amount")
  private BigDecimal bestBidSize;

  @JsonProperty("sell")
  private BigDecimal bestAskPrice;

  @JsonProperty("sell_amount")
  private BigDecimal bestAskSize;

  public BigDecimal get24hPercentageChange() {
    if (open24h != null && last != null && open24h.signum() > 0) {
      return last.divide(open24h, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
    return null;
  }
}
