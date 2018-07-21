package org.knowm.xchange.coinbene.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbeneCoinBalance {
  private final String asset;
  private final BigDecimal available;
  private final BigDecimal reserved;
  private final BigDecimal total;

  public CoinbeneCoinBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("reserved") BigDecimal reserved,
      @JsonProperty("total") BigDecimal total) {
    this.asset = asset;
    this.available = available;
    this.reserved = reserved;
    this.total = total;
  }

  public String getAsset() {
    return asset;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getReserved() {
    return reserved;
  }

  public BigDecimal getTotal() {
    return total;
  }
}
