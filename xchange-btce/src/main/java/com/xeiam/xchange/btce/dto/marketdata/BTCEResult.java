package com.xeiam.xchange.btce.dto.marketdata;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 */
public class BTCEResult<V> {
  private final boolean success;
  private final V value;
  private final String error;

  @JsonCreator
  public BTCEResult(@JsonProperty("success") boolean success, @JsonProperty("return") V value, @JsonProperty("error") String error) {

    this.success = success;
    this.value = value;
    this.error = error;
  }

/*
  @JsonCreator
  public BTCEResult(@JsonProperty("success") boolean success, @JsonProperty("error") String error) {

    this.success = success;
    this.error = error;
    this.value = null;
  }
*/

  public boolean isSuccess() {

    return success;
  }

  public V getValue() {

    return value;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("BTCEResult[%s: %s]", success ? "OK" : "error", success ? value.toString() : error);
  }
}
