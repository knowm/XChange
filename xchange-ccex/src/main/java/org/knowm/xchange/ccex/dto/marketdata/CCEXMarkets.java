package org.knowm.xchange.ccex.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXMarkets {
  private final Boolean success;
  private final String message;
  private List<CCEXMarket> result = new ArrayList<>();

  public CCEXMarkets(@JsonProperty("success") Boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") List<CCEXMarket> result) {
    super();
    this.success = success;
    this.message = message;
    this.result = result;
  }

  public Boolean getSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public List<CCEXMarket> getResult() {
    return result;
  }

}
