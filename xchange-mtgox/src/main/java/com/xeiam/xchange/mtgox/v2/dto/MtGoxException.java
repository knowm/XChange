package com.xeiam.xchange.mtgox.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi <br/>
 * @created 5/24/13 9:04 PM
 */
public class MtGoxException extends RuntimeException {
  @JsonProperty("error")
  private String error;

  @JsonProperty("result")
  private String result;

  @JsonProperty("token")
  private String token;

  public MtGoxException() { }

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

    return String.format("MtGoxException[token='%s', result='%s', error='%s']", token, result, error);
  }
}
