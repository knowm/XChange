package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author interwater */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinoneOrderBook {

  private final String result;
  private final String errorCode;
  private final String status;
  private final String timestamp;
  private final String currency;
  private final CoinoneOrderBookData[] asks;
  private final CoinoneOrderBookData[] bids;

  /**
   * @param result
   * @param errorCode
   * @param timestamp
   * @param currency
   */
  public CoinoneOrderBook(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("status") String status,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("currency") String currency,
      @JsonProperty("ask") CoinoneOrderBookData[] asks,
      @JsonProperty("bid") CoinoneOrderBookData[] bids) {
    this.result = result;
    this.errorCode = errorCode;
    this.timestamp = timestamp;
    this.currency = currency;
    this.asks = asks;
    this.bids = bids;
    this.status = status;
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getCurrency() {
    return currency;
  }

  public CoinoneOrderBookData[] getAsks() {
    return asks;
  }

  public CoinoneOrderBookData[] getBids() {
    return bids;
  }

  public String getStatus() {
    return status;
  }
}
