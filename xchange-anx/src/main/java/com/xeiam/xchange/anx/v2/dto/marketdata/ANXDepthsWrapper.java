package com.xeiam.xchange.anx.v2.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ANXDepthsWrapper {

  private final String result;
  private final Map<String, ANXDepth> anxDepths;
  private final String error;

  public ANXDepthsWrapper(@JsonProperty("result") String result, @JsonProperty("data") Map<String, ANXDepth> anxDepth, @JsonProperty("error") String error) {

    this.result = result;
    this.anxDepths = anxDepth;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public Map<String, ANXDepth> getAnxDepths() {

    return anxDepths;
  }

  public String getError() {

    return error;
  }

}
