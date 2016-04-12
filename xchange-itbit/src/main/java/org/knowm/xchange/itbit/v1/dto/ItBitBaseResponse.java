package org.knowm.xchange.itbit.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Piotr Ładyżyński
 */
public class ItBitBaseResponse {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String errorMessage;

  @JsonProperty("description")
  private String description;

  public String getCode() {

    return code;
  }

  public String getErrorMessage() {

    return errorMessage;
  }

  public String getDescription() {

    return description;
  }

  @Override
  public String toString() {

    return "HitbtcBaseResponse{" + "code='" + code + '\'' + ", message='" + errorMessage + '\'' + ", description='" + description + '\'' + '}';
  }
}
