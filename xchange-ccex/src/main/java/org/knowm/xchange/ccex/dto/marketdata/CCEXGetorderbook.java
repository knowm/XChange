package org.knowm.xchange.ccex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CCEXGetorderbook {

  private final Boolean success;
  private final String message;
  private final CCEXBuySellResult result;

  /**
   * @param message
   * @param result
   * @param success
   */
  public CCEXGetorderbook(
      @JsonProperty("success") Boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") CCEXBuySellResult result) {
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

  /** @return The result */
  public CCEXBuySellResult getResult() {
    return result;
  }

  /** (price, amount) */
  public List<CCEXBuySellData> getBids() {
    return this.result.getBuy();
  }

  /** (price, amount) */
  public List<CCEXBuySellData> getAsks() {
    return this.result.getSell();
  }
}
