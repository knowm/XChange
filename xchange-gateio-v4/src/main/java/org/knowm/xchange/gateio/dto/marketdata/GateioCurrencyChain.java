package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioCurrencyChain {

  @JsonProperty("chain")
  String chain;

  @JsonProperty("name_cn")
  String chainNameCN;

  @JsonProperty("name_en")
  String chainNameEN;

  @JsonProperty("is_disabled")
  Boolean disabled;

  @JsonProperty("is_deposit_disabled")
  Boolean depositDisabled;

  @JsonProperty("is_withdraw_disabled")
  Boolean withdrawDisabled;

  public boolean isWithdrawEnabled() {
    return isEnabled() && (withdrawDisabled != null) && !withdrawDisabled;
  }

  public boolean isDepositEnabled() {
    return isEnabled() && (depositDisabled != null) && !depositDisabled;
  }

  public boolean isEnabled() {
    return (disabled != null) && !disabled;
  }


}
