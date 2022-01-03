package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class FtxConvertSimulatePayloadRequestDto {

  @JsonProperty("fromCoin")
  private final Currency fromCoin;

  @JsonProperty("toCoin")
  private final Currency toCoin;

  @JsonProperty("size")
  private final BigDecimal size;

  @JsonCreator
  public FtxConvertSimulatePayloadRequestDto(
      @JsonProperty("fromCoin") Currency fromCoin,
      @JsonProperty("toCoin") Currency toCoin,
      @JsonProperty("size") BigDecimal size) {
    this.fromCoin = fromCoin;
    this.toCoin = toCoin;
    this.size = size;
  }

  public Currency getFromCoin() {
    return fromCoin;
  }

  public Currency getToCoin() {
    return toCoin;
  }

  public BigDecimal getSize() {
    return size;
  }
}
