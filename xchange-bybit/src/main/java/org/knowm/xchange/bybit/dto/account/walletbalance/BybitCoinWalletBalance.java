package org.knowm.xchange.bybit.dto.account.walletbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitCoinWalletBalance {

  @JsonProperty("coin")
  String coin;

  @JsonProperty("equity")
  String equity;

  @JsonProperty("usdValue")
  String usdValue;

  @JsonProperty("walletBalance")
  String walletBalance;

  @JsonProperty("free")
  String free;

  @JsonProperty("locked")
  String locked;

  @JsonProperty("borrowAmount")
  String borrowAmount;

  @JsonProperty("availableToBorrow")
  String availableToBorrow;

  @JsonProperty("availableToWithdraw")
  String availableToWithdraw;

  @JsonProperty("accruedInterest")
  String accruedInterest;

  @JsonProperty("totalOrderIM")
  String totalOrderIM;

  @JsonProperty("totalPositionIM")
  String totalPositionIM;

  @JsonProperty("totalPositionMM")
  String totalPositionMM;

  @JsonProperty("unrealisedPnl")
  String unrealisedPnl;

  @JsonProperty("cumRealisedPnl")
  String cumRealisedPnl;

  @JsonProperty("bonus")
  String bonus;

  @JsonProperty("collateralSwitch")
  boolean collateralSwitch;

  @JsonProperty("marginCollateral")
  boolean marginCollateral;
}
