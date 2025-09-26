package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinsph.dto.CoinsphResponse;

@Getter
@ToString
public class CoinsphAccount extends CoinsphResponse {

  private final BigDecimal makerCommission;
  private final BigDecimal takerCommission;
  private final BigDecimal buyerCommission;
  private final BigDecimal sellerCommission;
  private final boolean canTrade;
  private final boolean canWithdraw;
  private final boolean canDeposit;
  private final long updateTime;
  private final String accountType; // e.g., SPOT
  private final List<CoinsphBalance> balances;
  private final List<String> permissions; // e.g., ["SPOT"]

  public CoinsphAccount(
      @JsonProperty("makerCommission") BigDecimal makerCommission,
      @JsonProperty("takerCommission") BigDecimal takerCommission,
      @JsonProperty("buyerCommission") BigDecimal buyerCommission,
      @JsonProperty("sellerCommission") BigDecimal sellerCommission,
      @JsonProperty("canTrade") boolean canTrade,
      @JsonProperty("canWithdraw") boolean canWithdraw,
      @JsonProperty("canDeposit") boolean canDeposit,
      @JsonProperty("updateTime") long updateTime,
      @JsonProperty("accountType") String accountType,
      @JsonProperty("balances") List<CoinsphBalance> balances,
      @JsonProperty("permissions") List<String> permissions) {
    this.makerCommission = makerCommission;
    this.takerCommission = takerCommission;
    this.buyerCommission = buyerCommission;
    this.sellerCommission = sellerCommission;
    this.canTrade = canTrade;
    this.canWithdraw = canWithdraw;
    this.canDeposit = canDeposit;
    this.updateTime = updateTime;
    this.accountType = accountType;
    this.balances = balances;
    this.permissions = permissions;
  }
}
