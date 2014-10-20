package com.xeiam.xchange.bitbay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BitbayBaseResponse {

  private final String code;
  private final String message;
  private final String success;

    public BitbayBaseResponse(@JsonProperty("code") String code, @JsonProperty("message")  String message, @JsonProperty("success") String success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }
}
