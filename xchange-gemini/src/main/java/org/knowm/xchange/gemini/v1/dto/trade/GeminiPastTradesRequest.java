package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiPastTradesRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("symbol")
  protected String symbol;

  @JsonProperty("timestamp")
  protected long timestamp;

  @JsonProperty("limit_trades")
  protected int limitTrades;

  /**
   * Constructor
   *
   * @param nonce
   * @param symbol
   * @param timestamp
   * @param limitTrades
   */
  public GeminiPastTradesRequest(String nonce, String symbol, long timestamp, int limitTrades) {

    this.request = "/v1/mytrades";
    this.nonce = nonce;
    this.symbol = symbol;
    this.timestamp = timestamp;
    this.limitTrades = limitTrades;
  }
}