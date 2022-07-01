package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeCompactOrders {

  BitcoindeCompactOrder[] bids;
  BitcoindeCompactOrder[] asks;

  @JsonCreator
  public BitcoindeCompactOrders(
      @JsonProperty("bids") BitcoindeCompactOrder[] bids,
      @JsonProperty("asks") BitcoindeCompactOrder[] asks) {
    this.bids = bids;
    this.asks = asks;
  }
}
