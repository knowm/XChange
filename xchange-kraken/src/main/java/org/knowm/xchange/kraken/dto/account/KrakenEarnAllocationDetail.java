package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class KrakenEarnAllocationDetail {
  @JsonProperty("native")
  private final BigDecimal nativeAmount;

  @JsonProperty("converted")
  private final BigDecimal convertedAmount;

  @JsonProperty("created_at")
  private final Date createdAt;

  @JsonProperty("expires")
  private final Date expires;
}
