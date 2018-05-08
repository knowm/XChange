package org.knowm.xchange.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Raphael Voellmy */
public class KrakenResult<V> {

  private final V result;
  private final String[] error;

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  @JsonCreator
  public KrakenResult(@JsonProperty("return") V result, @JsonProperty("error") String[] error) {

    this.result = result;
    this.error = error;
  }

  public boolean isSuccess() {

    return error.length == 0;
  }

  public V getResult() {

    return result;
  }

  public String[] getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format(
        "KrakenResult[%s: %s]",
        isSuccess() ? "OK" : "error", isSuccess() ? result.toString() : error);
  }
}
