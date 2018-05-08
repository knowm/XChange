package org.knowm.xchange.mercadobitcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Felipe Micaroni Lalli */
public final class OperationEntry {

  private final BigDecimal volume;
  private final BigDecimal price;
  private final BigDecimal rate;
  private final Long created;

  public OperationEntry(
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("created") Long created) {

    this.volume = volume;
    this.price = price;
    this.rate = rate;
    this.created = created;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public Long getCreated() {

    return created;
  }

  @Override
  public String toString() {

    return "OperationEntry ["
        + "volume="
        + volume
        + ", price="
        + price
        + ", rate="
        + rate
        + ", created="
        + created
        + ']';
  }
}
