package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

public final class CmcStatus {

  private final Date timestamp;
  private final int errorCode;
  private final String errorMessage;
  private final int elapsed;
  private final int creditCount;

  public CmcStatus(
      @JsonProperty("timestamp") @JsonDeserialize(using = ISO8601DateDeserializer.class)
          Date timestamp,
      @JsonProperty("error_code") int errorCode,
      @JsonProperty("error_message") String errorMessage,
      @JsonProperty("elapsed") int elapsed,
      @JsonProperty("credit_count") int creditCount) {
    this.timestamp = timestamp;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.elapsed = elapsed;
    this.creditCount = creditCount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public int getElapsed() {
    return elapsed;
  }

  public int getCreditCount() {
    return creditCount;
  }

  @Override
  public String toString() {
    return "CmcStatus{"
        + "timestamp='"
        + timestamp
        + '\''
        + ", errorCode="
        + errorCode
        + ", errorMessage='"
        + errorMessage
        + '\''
        + ", elapsed="
        + elapsed
        + ", creditCount="
        + creditCount
        + '}';
  }
}
