package org.knowm.xchange.clevercoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.clevercoin.dto.CleverCoinBaseResponse;

public class CleverCoinDepositAddress extends CleverCoinBaseResponse {

  private final String depositAddress;

  protected CleverCoinDepositAddress(@JsonProperty("address") String depositAddress, @JsonProperty("error") String error) {

    super(error);
    this.depositAddress = depositAddress;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  @Override
  public String toString() {

    return "CleverCoinDepositAddress [depositAddress=" + depositAddress + "]";
  }

}
