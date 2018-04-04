package org.knowm.xchange.ccex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class CCEXTrades {
  private final Boolean success;
  private final String message;
  private List<CCEXTrade> result = new ArrayList<>();

  /**
   * @param message
   * @param result
   * @param success
   */
  public CCEXTrades(
      @JsonProperty("success") Boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") List<CCEXTrade> result) {
    this.success = success;
    this.message = message;
    this.result = result;
  }

  /** @return The success */
  public Boolean getSuccess() {
    return success;
  }

  /** @return The message */
  public String getMessage() {
    return message;
  }

  /** @return The message */
  public List<CCEXTrade> getResult() {
    return result;
  }
}
