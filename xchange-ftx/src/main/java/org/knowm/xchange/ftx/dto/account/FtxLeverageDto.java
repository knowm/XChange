package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxLeverageDto {

  private final int leverage;

  public FtxLeverageDto(@JsonProperty("leverage") int leverage) {
    this.leverage = leverage;
  }

  public int getLeverage() {
    return leverage;
  }

  @Override
  public String toString() {
    return "FtxLeverageDto{" + "leverage=" + leverage + '}';
  }
}
