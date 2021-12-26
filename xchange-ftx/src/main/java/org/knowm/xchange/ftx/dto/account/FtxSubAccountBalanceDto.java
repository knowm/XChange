package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FtxSubAccountBalanceDto {

  private final String coin;

  private final BigDecimal free;

  private final BigDecimal total;

  public FtxSubAccountBalanceDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("free") BigDecimal free,
      @JsonProperty("total") BigDecimal total) {
    this.coin = coin;
    this.free = free;
    this.total = total;
  }

  public String getCoin() {
    return coin;
  }

  public BigDecimal getFree() {
    return free;
  }

  public BigDecimal getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "FtxSubAccountBalanceDto{"
        + "coin='"
        + coin
        + '\''
        + ", free="
        + free
        + ", total="
        + total
        + '}';
  }
}
