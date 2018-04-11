package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ANXTickersWrapper {

  private final String result;
  private final Map<String, ANXTicker> anxTickers;
  private final String error;

  public ANXTickersWrapper(
      @JsonProperty("result") String result,
      @JsonProperty("data") Map<String, ANXTicker> anxTickers,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxTickers = anxTickers;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public Map<String, ANXTicker> getAnxTickers() {

    return anxTickers;
  }

  public String getError() {

    return error;
  }
}
