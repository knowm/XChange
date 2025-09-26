package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphBalance {

  private final String asset;
  private final BigDecimal free;
  private final BigDecimal locked;

  public CoinsphBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("free") BigDecimal free,
      @JsonProperty("locked") BigDecimal locked) {
    this.asset = asset;
    this.free = free;
    this.locked = locked;
  }
}
