package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Panchen
 */
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
