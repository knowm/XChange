package org.knowm.xchange.coincheck.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoincheckTicker {
  @JsonProperty BigDecimal last;
  @JsonProperty BigDecimal bid;
  @JsonProperty BigDecimal ask;
  @JsonProperty BigDecimal high;
  @JsonProperty BigDecimal low;
  @JsonProperty BigDecimal volume;
  @JsonProperty long timestamp;
}
