package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

public class FtxWalletBalanceDto {

  @JsonProperty("coin")
  private Currency coin;

  @JsonProperty("free")
  private BigDecimal free;

  @JsonProperty("total")
  private BigDecimal total;

  public FtxWalletBalanceDto(
      @JsonProperty("coin") Currency coin,
      @JsonProperty("free") BigDecimal free,
      @JsonProperty("total") BigDecimal total) {
    this.coin = coin;
    this.free = free;
    this.total = total;
  }

  public Currency getCoin() {
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
    return "FtxWalletBalanceDto{" + "coin=" + coin + ", free=" + free + ", total=" + total + '}';
  }
}
