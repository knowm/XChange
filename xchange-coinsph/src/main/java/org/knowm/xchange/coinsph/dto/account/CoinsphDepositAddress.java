package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinsph.dto.CoinsphResponse;

/**
 * Represents a deposit address from Coins.ph API Based on GET /openapi/wallet/v1/deposit/address
 * endpoint
 */
@Getter
@ToString
public class CoinsphDepositAddress extends CoinsphResponse {

  private final String coin;
  private final String address;
  private final String addressTag;

  public CoinsphDepositAddress(
      @JsonProperty("coin") String coin,
      @JsonProperty("address") String address,
      @JsonProperty("addressTag") String addressTag) {
    this.coin = coin;
    this.address = address;
    this.addressTag = addressTag;
  }
}
