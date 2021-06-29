package info.bitrich.xchangestream.ftx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class FtxOrderbookResponse implements Serializable {

  @JsonProperty("time")
  private final Date time;

  @JsonProperty("checksum")
  private final Long checksum;

  @JsonProperty("bids")
  @JsonIgnore
  private final List<List<BigDecimal>> bids;

  @JsonProperty("asks")
  @JsonIgnore
  private final List<List<BigDecimal>> asks;

  @JsonProperty("action")
  private final String action;

  public FtxOrderbookResponse(
      @JsonProperty("time") Long time,
      @JsonProperty("checksum") Long checksum,
      @JsonProperty("bids") List<List<BigDecimal>> bids,
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("action") String action) {
    this.time = Date.from(Instant.ofEpochMilli(time));
    this.checksum = checksum;
    this.bids = bids;
    this.asks = asks;
    this.action = action;
  }

  public Date getTime() {
    return time;
  }

  public Long getChecksum() {
    return checksum;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public String getAction() {
    return action;
  }

  @Override
  public String toString() {
    return "FtxOrderbookResponse{"
        + "time="
        + time
        + ", checksum='"
        + checksum
        + '\''
        + ", bids="
        + bids
        + ", asks="
        + asks
        + ", action='"
        + action
        + '\''
        + '}';
  }
}
