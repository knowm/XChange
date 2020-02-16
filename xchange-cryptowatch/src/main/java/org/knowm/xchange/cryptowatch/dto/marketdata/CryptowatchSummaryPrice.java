package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CryptowatchSummaryPrice {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final CryptowatchPriceChange change;

  public CryptowatchSummaryPrice(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("change") CryptowatchPriceChange change) {
    this.high = high;
    this.low = low;
    this.last = last;
    this.change = change;
  }
}
