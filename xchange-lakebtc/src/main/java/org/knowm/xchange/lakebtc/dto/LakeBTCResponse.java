package org.knowm.xchange.lakebtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:31 PM */
public class LakeBTCResponse<V> {

  private final String id;
  private final V result;

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public LakeBTCResponse(@JsonProperty("id") String id, @JsonProperty("result") V result) {
    this.id = id;
    this.result = result;
  }

  public V getResult() {
    return result;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.format("LakeBTCResponse{id=%s, result=%s}", id, result);
  }
}
