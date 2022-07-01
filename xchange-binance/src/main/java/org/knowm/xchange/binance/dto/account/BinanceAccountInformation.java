package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public final class BinanceAccountInformation {

  public final BigDecimal makerCommission;
  public final BigDecimal takerCommission;
  public final BigDecimal buyerCommission;
  public final BigDecimal sellerCommission;
  public final boolean canTrade;
  public final boolean canWithdraw;
  public final boolean canDeposit;
  public final long updateTime;
  public List<BinanceBalance> balances;
  public List<String> permissions;

  public BinanceAccountInformation(
      @JsonProperty("makerCommission") BigDecimal makerCommission,
      @JsonProperty("takerCommission") BigDecimal takerCommission,
      @JsonProperty("buyerCommission") BigDecimal buyerCommission,
      @JsonProperty("sellerCommission") BigDecimal sellerCommission,
      @JsonProperty("canTrade") boolean canTrade,
      @JsonProperty("canWithdraw") boolean canWithdraw,
      @JsonProperty("canDeposit") boolean canDeposit,
      @JsonProperty("updateTime") long updateTime,
      @JsonProperty("balances") List<BinanceBalance> balances,
      @JsonProperty("permissions") List<String> permissions) {
    this.makerCommission = makerCommission;
    this.takerCommission = takerCommission;
    this.buyerCommission = buyerCommission;
    this.sellerCommission = sellerCommission;
    this.canTrade = canTrade;
    this.canWithdraw = canWithdraw;
    this.canDeposit = canDeposit;
    this.updateTime = updateTime;
    this.balances = balances;
    this.permissions = permissions;
  }
}
