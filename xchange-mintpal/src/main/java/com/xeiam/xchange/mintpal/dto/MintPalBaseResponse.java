package com.xeiam.xchange.mintpal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MintPalBaseResponse<V> {

  private final V data;
  private final String status;
  private final String message;

  public MintPalBaseResponse(@JsonProperty("data") V data, @JsonProperty("status") String status, @JsonProperty("message") String message) {

    this.data = data;
    this.status = status;
    this.message = message;
  }

  public V getData() {

    return data;
  }

  public String getStatus() {

    return status;
  }

  public String getMessage() {

    return message;
  }

  @Override
  public String toString() {

    return "MintPalBaseResponse [data=" + data + ", status=" + status + ", message=" + message + "]";
  }
}
