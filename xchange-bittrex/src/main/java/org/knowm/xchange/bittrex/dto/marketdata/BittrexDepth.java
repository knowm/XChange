package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bittrex.BittrexConstants;
import si.mazi.rescu.HttpResponseAware;

public class BittrexDepth implements HttpResponseAware {

  private Map<String, List<String>> headers;
  private final BittrexLevel[] asks;
  private final BittrexLevel[] bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public BittrexDepth(
      @JsonProperty("ask") BittrexLevel[] asks, @JsonProperty("bid") BittrexLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BittrexLevel[] getAsks() {

    return asks;
  }

  public BittrexLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BittrexDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }

  @Override
  public void setResponseHeaders(Map<String, List<String>> headers) {
    this.headers = headers;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return headers;
  }

  public String getHeader(String key) {
    return getResponseHeaders().get(key).get(0);
  }

  public String getSequence() {
    return getResponseHeaders().get(BittrexConstants.SEQUENCE).get(0);
  }
}
