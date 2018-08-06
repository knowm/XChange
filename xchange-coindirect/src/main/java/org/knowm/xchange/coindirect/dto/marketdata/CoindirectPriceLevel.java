package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectPriceLevel {
  public final BigDecimal size;
  public final BigDecimal price;

  public CoindirectPriceLevel(
      @JsonProperty("size") BigDecimal size, @JsonProperty("price") BigDecimal price) {
    this.size = size;
    this.price = price;
  }

  @Override
  public String toString() {
    return "CoindirectPriceLevel{" + "size=" + size + ", price=" + price + '}';
  }
}
