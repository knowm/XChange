package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoinmateNetworkInfo {

  private final String network;
  private final CoinmateDepositInfo deposit;
  private final CoinmateWithdrawInfo withdraw;

  @JsonCreator
  public CoinmateNetworkInfo(
      @JsonProperty("network") String network,
      @JsonProperty("deposit") CoinmateDepositInfo deposit,
      @JsonProperty("withdraw") CoinmateWithdrawInfo withdraw) {
    this.network = network;
    this.deposit = deposit;
    this.withdraw = withdraw;
  }
}
