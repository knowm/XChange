package org.knowm.xchange.bitbay.dto.acount;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Z. Dolezal
 */
public class BitbayBalance {
  private final BigDecimal available;
  private final BigDecimal locked;

  /**
   * available : amount of available money/cryptocurrency locked : amount of locked money/cryptocurrency
   */
  public BitbayBalance(@JsonProperty("available") BigDecimal available, @JsonProperty("locked") BigDecimal locked) {
    this.available = available;
    this.locked = locked;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getLocked() {
    return locked;
  }
}
