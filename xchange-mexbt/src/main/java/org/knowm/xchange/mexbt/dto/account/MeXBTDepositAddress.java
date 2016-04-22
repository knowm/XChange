package org.knowm.xchange.mexbt.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTDepositAddress {

  private final String name;
  private final String depositAddress;

  public MeXBTDepositAddress(@JsonProperty("name") String name, @JsonProperty("depositAddress") String depositAddress) {
    this.name = name;
    this.depositAddress = depositAddress;
  }

  public String getName() {
    return name;
  }

  public String getDepositAddress() {
    return depositAddress;
  }

}
