package org.knowm.xchange.cryptsy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */
public class CryptsyGenericReturn<V> {

  private final boolean success;
  private final V returnValue;
  private final String error;

  @JsonCreator
  public CryptsyGenericReturn(@JsonProperty("success") int success, @JsonProperty("return") V returnValue, @JsonProperty("error") String error) {

    this.success = (success == 1 ? true : false);
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

    return String.format("CryptsyReturn[%s: %s]", success ? "OK" : "error", success ? returnValue.toString() : error);
  }
}
