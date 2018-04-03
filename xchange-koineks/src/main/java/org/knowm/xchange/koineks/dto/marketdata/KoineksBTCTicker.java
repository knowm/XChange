package org.knowm.xchange.koineks.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

/** Created by semihunaldi on 05/12/2017 */
public class KoineksBTCTicker extends BaseKoineksTicker {
  public KoineksBTCTicker(
      @JsonProperty("short_code") Currency shortCode,
      @JsonProperty("name") String name,
      @JsonProperty("currency") Currency currency,
      @JsonProperty("current") BigDecimal current,
      @JsonProperty("change_amount") String changeAmount,
      @JsonProperty("change_percentage") BigDecimal changePercentage,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("timestamp") String timestamp) {
    super(
        shortCode,
        name,
        currency,
        current,
        changeAmount,
        changePercentage,
        high,
        low,
        volume,
        ask,
        bid,
        timestamp);
  }
}
