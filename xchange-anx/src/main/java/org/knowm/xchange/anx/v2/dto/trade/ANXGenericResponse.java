package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a response message from ANX after placing and order
 */
public final class ANXGenericResponse {

  private final String result;
  private final Object data;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param data
   * @param error
   */
  public ANXGenericResponse(@JsonProperty("result") String result, @JsonProperty("data") Object data, @JsonProperty("error") String error) {

    this.result = result;
    this.data = data;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public Object getData() {

    return data;
  }

  public String getError() {

    return error;
  }

  public String getDataString() {

    return data == null ? null : data.toString();
  }

  @Override
  public String toString() {

    return "ANXGenericResponse [result=" + result + ", data=" + data + ", error=" + error + "]";
  }

}
