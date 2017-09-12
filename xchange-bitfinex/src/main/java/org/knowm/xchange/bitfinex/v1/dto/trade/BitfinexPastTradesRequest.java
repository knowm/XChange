package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexPastTradesRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("symbol")
  protected String symbol;

  /**
   * REQUIRED Trades made before this timestamp won’t be returned
   */
  @JsonProperty("timestamp")
  protected long startTime;

  /**
   * Trades made after this timestamp won’t be returned.
   */
  @JsonProperty("until")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  protected Long endTime;

  @JsonProperty("limit_trades")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  protected Integer limitTrades;

  public BitfinexPastTradesRequest(String nonce, String symbol, long startTime, Long endTime, Integer limitTrades) {

    this.request = "/v1/mytrades";
    this.nonce = nonce;
    this.symbol = symbol;
    this.startTime = startTime;
    this.endTime = endTime;
    this.limitTrades = limitTrades;
  }
}