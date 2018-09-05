package org.knowm.xchange.bittrex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.ExceptionalReturnContentException;

/** @author walec51 */
@JsonIgnoreProperties("message")
public class BittrexBaseReponse<T> {

  private final T result;

  public BittrexBaseReponse(
      @JsonProperty("success") boolean success, @JsonProperty("result") T result) {
    if (!success) {
      throw new ExceptionalReturnContentException("Success set to false in response");
    }
    this.result = result;
  }

  public T getResult() {
    return result;
  }
}
