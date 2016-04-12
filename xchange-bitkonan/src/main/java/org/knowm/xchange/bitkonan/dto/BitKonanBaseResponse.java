package org.knowm.xchange.bitkonan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanBaseResponse {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  public String getCode() {

    return code;
  }

  public String getMessage() {

    return message;
  }

  @Override
  public String toString() {

    return "BitKonanBaseResponse{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
  }
}
