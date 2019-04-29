package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** V represents result class of the queried endpoint */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitResponse<V> {

  @JsonProperty("id")
  private long id;

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

  public DeribitResponse(
      long id, V result, DeribitError error, boolean testnet, long usIn, long usOut, long usDiff) {
    this.id = id;
    this.result = result;
    this.error = error;
    this.testnet = testnet;
    this.usIn = usIn;
    this.usOut = usOut;
    this.usDiff = usDiff;
  }

  public long getId() {
    return id;
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
