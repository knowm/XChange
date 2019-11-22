package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitfinexEmptyRequest {

  @JsonProperty("request")
  protected String request;

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

}
