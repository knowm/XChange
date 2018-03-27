package org.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

// TODO: Extend POJO To Remove Annotation
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitZPublicTrade {

  private final BigDecimal price;
  private final BigDecimal volume;

  public BitZPublicTrade(
      @JsonProperty("p") BigDecimal price, @JsonProperty("n") BigDecimal volume) {
    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }
}
