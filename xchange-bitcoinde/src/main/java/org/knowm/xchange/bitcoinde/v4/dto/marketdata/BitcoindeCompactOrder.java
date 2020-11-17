package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeCompactOrder {

  BigDecimal price;
  BigDecimal amount;

  @JsonCreator
  public BitcoindeCompactOrder(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount_currency_to_trade") BigDecimal amount) {
    this.price = price;
    this.amount = amount;
  }
}
