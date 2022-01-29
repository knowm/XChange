package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxConvertSimulatePayloadRequestDto {

  @JsonProperty("fromCoin")
  private final String fromCoin;

  @JsonProperty("toCoin")
  private final String toCoin;

  @JsonProperty("size")
  private final double size;

  @JsonCreator
  public FtxConvertSimulatePayloadRequestDto(
      @JsonProperty("fromCoin") String fromCoin,
      @JsonProperty("toCoin") String toCoin,
      @JsonProperty("size") double size) {
    this.fromCoin = fromCoin;
    this.toCoin = toCoin;
    this.size = size;
  }

  public String getFromCoin() {
    return fromCoin;
  }

  public String getToCoin() {
    return toCoin;
  }

  public double getSize() {
    return size;
  }
}
