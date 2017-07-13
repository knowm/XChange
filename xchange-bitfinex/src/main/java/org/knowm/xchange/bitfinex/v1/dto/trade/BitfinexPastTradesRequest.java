package org.knowm.xchange.bitfinex.v1.dto.trade;

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
  protected Long endTime;

  @JsonProperty("limit_trades")
  protected int limitTrades;

  public BitfinexPastTradesRequest(String nonce, String symbol, long startTime, Long endTime, int limitTrades) {

    this.request = "/v1/mytrades";
    this.nonce = nonce;
    this.symbol = symbol;
    this.startTime = startTime;
    this.endTime = endTime;
    this.limitTrades = limitTrades;
  }
}