package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"asks", "bids"})
public class GlobitexOrderBook implements Serializable {

  @JsonProperty("asks")
  private final List<GlobitexOrder> asks;

  @JsonProperty("bids")
  private final List<GlobitexOrder> bids;

  /**
   * @param asks
   * @param bids
   */
  public GlobitexOrderBook(
      @JsonProperty("asks") List<GlobitexOrder> asks,
      @JsonProperty("bids") List<GlobitexOrder> bids) {
    super();
    this.asks = asks;
    this.bids = bids;
  }

  public List<GlobitexOrder> getAsks() {
    return asks;
  }

  public List<GlobitexOrder> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "GlobitexOrderBook{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
