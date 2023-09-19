package org.knowm.xchange.bybit.dto.account.walletbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitAccountBalance {

  @JsonProperty("accountType")
  BybitAccountType accountType;

  @JsonProperty("accountLTV")
  String accountLTV;

  @JsonProperty("accountIMRate")
  String accountIMRate;

  @JsonProperty("accountMMRate")
  String accountMMRate;

  @JsonProperty("totalEquity")
  String totalEquity;

  @JsonProperty("totalWalletBalance")
  String totalWalletBalance;

  @JsonProperty("totalMarginBalance")
  String totalMarginBalance;

  @JsonProperty("totalAvailableBalance")
  String totalAvailableBalance;

  @JsonProperty("totalPerpUPL")
  String totalPerpUPL;

  @JsonProperty("totalInitialMargin")
  String totalInitialMargin;

  @JsonProperty("totalMaintenanceMargin")
  String totalMaintenanceMargin;

  @JsonProperty("coin")
  List<BybitCoinWalletBalance> coin;
}
