package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class OkexOrderBookEntry {

  private BigDecimal price;
  private BigDecimal volume;
  private String numOrdersOnLevel;

  @JsonCreator
  public OkexOrderBookEntry(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("numOrdersOnLevel") String numOrdersOnLevel) {

    this.price = price;
    this.volume = volume;
    this.numOrdersOnLevel = numOrdersOnLevel;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public String getNumOrdersOnLevel() {
    return numOrdersOnLevel;
  }

  @Override
  public String toString() {
    return "OkexOrderBookEntry [price="
        + price
        + ","
        + " volume="
        + volume
        + ","
        + " numOrdersOnLevel="
        + numOrdersOnLevel
        + "]";
  }
}
