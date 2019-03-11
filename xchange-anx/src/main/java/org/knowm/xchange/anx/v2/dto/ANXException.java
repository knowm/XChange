package org.knowm.xchange.anx.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Matija Mazi */
public class ANXException extends RuntimeException {

  @JsonProperty("error")
  private String error;

  @JsonProperty("result")
  private String result;

  @JsonProperty("token")
  private String token;

  /** Constructor */
  public ANXException() {}

  public String getError() {

    return error;
  }

  public String getResult() {

    return result;
  }

  public String getToken() {

    return token;
  }

  @Override
  public String toString() {

    return String.format("ANXException[token='%s', result='%s', error='%s']", token, result, error);
  }
}
