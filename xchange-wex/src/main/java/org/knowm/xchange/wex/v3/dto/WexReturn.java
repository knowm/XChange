package org.knowm.xchange.wex.v3.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Matija Mazi */
public class WexReturn<V> {

  private final boolean success;
  private final V returnValue;
  private final String error;

  /**
   * Constructor
   *
   * @param success
   * @param returnValue
   * @param error
   */
  @JsonCreator
  public WexReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") V returnValue,
      @JsonProperty("error") String error) {

    this.success = success;
    this.returnValue = returnValue;
    this.error = error;
  }

  public boolean isSuccess() {

    return success;
  }

  public V getReturnValue() {

    return returnValue;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format(
        "WexReturn[%s: %s]", success ? "OK" : "error", success ? returnValue.toString() : error);
  }
}
