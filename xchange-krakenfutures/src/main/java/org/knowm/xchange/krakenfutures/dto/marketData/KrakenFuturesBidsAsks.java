package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesBidsAsks {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  public KrakenFuturesBidsAsks(
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("bids") List<List<BigDecimal>> bids) {

    this.bids = bids;
    this.asks = asks;
  }
}
