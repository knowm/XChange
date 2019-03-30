package org.knowm.xchange.deribit.v1.dto;

import com.fasterxml.jackson.annotation.*;

/** V represents result class of the queried endpoint */
public class DeribitResponse<V> {

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("error")
  private int error;

  @JsonProperty("testnet")
  private boolean testnet;

  @JsonProperty("message")
  private String message;

  @JsonProperty("usOut")
  private long usOut;

  @JsonProperty("usIn")
  private long usIn;

  @JsonProperty("usDiff")
  private long usDiff;

  @JsonProperty("result")
  private V result;


  public DeribitResponse(boolean success, int error, boolean testnet, String message, long usOut, long usIn, long usDiff, V result) {
    this.success = success;
    this.error = error;
    this.testnet = testnet;
    this.message = message;
    this.usOut = usOut;
    this.usIn = usIn;
    this.usDiff = usDiff;
    this.result = result;
  }

  public boolean isSuccess() {
    return success;
  }

  public int getError() {
    return error;
  }

  public boolean isTestnet() {
    return testnet;
  }

  public String getMessage() {
    return message;
  }

  public long getUsOut() {
    return usOut;
  }

  public long getUsIn() {
    return usIn;
  }

  public long getUsDiff() {
    return usDiff;
  }

  public V getResult() {
    return result;
  }
}
