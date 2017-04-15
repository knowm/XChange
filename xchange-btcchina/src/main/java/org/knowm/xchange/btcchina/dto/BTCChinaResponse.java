package org.knowm.xchange.btcchina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaResponse<V> {

  private final String id;
  private final V result;
  private final BTCChinaError error;

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public BTCChinaResponse(@JsonProperty("id") String id, @JsonProperty("result") V result, @JsonProperty("error") BTCChinaError error) {

    this.id = id;
    this.result = result;
    this.error = error;

  }

  public V getResult() {

    return result;
  }

  public String getId() {

    return id;
  }

  public BTCChinaError getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaResponse{id=%s, result=%s}", id, result);
  }

}
