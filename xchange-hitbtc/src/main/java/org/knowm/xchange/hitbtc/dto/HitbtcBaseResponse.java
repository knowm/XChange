package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class HitbtcBaseResponse {

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

    return "HitbtcBaseResponse{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
  }
}
