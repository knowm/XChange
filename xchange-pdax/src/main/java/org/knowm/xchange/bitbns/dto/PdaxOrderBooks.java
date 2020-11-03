package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrderBooks {

  private PdaxOrderBook data;

  public PdaxOrderBooks(@JsonProperty("data") PdaxOrderBook data) {
    this.data = data;
  }

  public PdaxOrderBook getData() {
    return data;
  }

  public void setData(PdaxOrderBook data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "PdaxOrderBooks [data=" + data + "]";
  }
}
