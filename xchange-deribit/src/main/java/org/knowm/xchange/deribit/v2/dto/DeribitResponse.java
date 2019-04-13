package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** V represents result class of the queried endpoint */
public class DeribitResponse<V> {

  @JsonProperty("id")
  private long success;

  @JsonProperty("result")
  private V result;

  @JsonProperty("error")
  private DeribitError error;

  @JsonProperty("testnet")
  private boolean testnet;

  @JsonProperty("usIn")
  private long usIn;

  @JsonProperty("usOut")
  private long usOut;

  @JsonProperty("usDiff")
  private long usDiff;

  public DeribitResponse(long success, V result, DeribitError error, boolean testnet, long usIn, long usOut, long usDiff) {
    this.success = success;
    this.result = result;
    this.error = error;
    this.testnet = testnet;
    this.usIn = usIn;
    this.usOut = usOut;
    this.usDiff = usDiff;
  }

  public long getSuccess() {
    return success;
  }

  public V getResult() {
    return result;
  }

  public DeribitError getError() {
    return error;
  }

  public boolean isTestnet() {
    return testnet;
  }

  public long getUsIn() {
    return usIn;
  }

  public long getUsOut() {
    return usOut;
  }

  public long getUsDiff() {
    return usDiff;
  }
}
