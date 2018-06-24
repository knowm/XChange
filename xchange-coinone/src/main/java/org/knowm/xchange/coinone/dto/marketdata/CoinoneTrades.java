package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author interwater */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinoneTrades {

  private final String result;
  private final String errorCode;
  private final String timestamp;
  private final String currency;
  private final CoinoneTradeData[] completeOrders;

  /**
   * @param result
   * @param errorCode
   * @param timestamp
   * @param currency
   */
  public CoinoneTrades(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("currency") String currency,
      @JsonProperty("completeOrders") CoinoneTradeData[] completeOrders) {
    this.result = result;
    this.errorCode = errorCode;
    this.timestamp = timestamp;
    this.currency = currency;
    this.completeOrders = completeOrders;
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

  public CoinoneTradeData[] getCompleteOrders() {
    return completeOrders;
  }
}
