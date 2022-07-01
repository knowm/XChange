package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import si.mazi.rescu.ExceptionalReturnContentException;

/** V represents result class of the queried endpoint */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitResponse<V> {

  @JsonProperty("jsonrpc")
  private String jsonRPC;

  @JsonProperty("id")
  private long id;

  @JsonProperty("result")
  private V result;

  @JsonProperty("testnet")
  private boolean testnet;

  @JsonProperty("usIn")
  private long usIn;

  @JsonProperty("usOut")
  private long usOut;

  @JsonProperty("usDiff")
  private long usDiff;

  public DeribitResponse() {}

  public DeribitResponse(
      String jsonRPC,
      long id,
      V result,
      DeribitError error,
      boolean testnet,
      long usIn,
      long usOut,
      long usDiff) {

    /** This will cause parsing the response body as an DeribitException */
    if (error != null) {
      throw new ExceptionalReturnContentException("Error occurred");
    }

    this.jsonRPC = jsonRPC;
    this.id = id;
    this.result = result;
    this.testnet = testnet;
    this.usIn = usIn;
    this.usOut = usOut;
    this.usDiff = usDiff;
  }
}
