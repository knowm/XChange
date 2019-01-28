package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CexioWebSocketOrderBookSubscribeResponse {
  @JsonProperty public final Date timestamp;

  @JsonProperty public final List<List<BigDecimal>> bids;

  @JsonProperty public final List<List<BigDecimal>> asks;

  @JsonProperty public final String pair;

  @JsonProperty public final BigInteger id;

  public CexioWebSocketOrderBookSubscribeResponse(
      @JsonProperty(value = "timestamp", required = false) Date timestamp,
      @JsonProperty(value = "time", required = false) Date time,
      @JsonProperty("bids") List<List<BigDecimal>> bids,
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("pair") String pair,
      @JsonProperty("id") BigInteger id) {
    if (timestamp == null && time == null) {
      throw new IllegalArgumentException("Both time and timestamp cannot be null");
    }
    this.timestamp = timestamp != null ? timestamp : time;
    this.bids = bids;
    this.asks = asks;
    this.pair = pair;
    this.id = id;
  }
}
