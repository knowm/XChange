package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioCurrencyInfo {

  @JsonProperty("currency")
  String currencyWithChain;

  @JsonProperty("delisted")
  Boolean delisted;

  @JsonProperty("withdraw_disabled")
  Boolean withdrawDisabled;

  @JsonProperty("withdraw_delayed")
  Boolean withdrawDelayed;

  @JsonProperty("deposit_disabled")
  Boolean depositDisabled;

  @JsonProperty("trade_disabled")
  Boolean tradeDisabled;

  @JsonProperty("chain")
  String chain;


  public boolean isWithdrawEnabled() {
    return (withdrawDisabled != null) && !withdrawDisabled;
  }


  public boolean isDepositEnabled() {
    return (depositDisabled != null) && !depositDisabled;
  }

}
