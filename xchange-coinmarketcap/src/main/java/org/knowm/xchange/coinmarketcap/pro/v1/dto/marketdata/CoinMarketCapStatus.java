package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CoinMarketCapStatus {

  private final String timestamp;
  private final int errorCode;
  private final String errorMessage;
  private final int elapsed;
  private final int creditCount;

  public CoinMarketCapStatus(
      @JsonProperty("timestamp") String timestamp,
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

  public String getTimestamp() {
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
    return "CoinMarketCapStatus{"
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
