package org.knowm.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;

import java.util.Map;

/** @author Raphael Voellmy */
public class KrakenOrderBookResult {

  private final String result;
  private final String serverTime;
  private final String error;
  private final KrakenDepth orderbook;


  public KrakenOrderBookResult(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("orderBook") KrakenDepth orderbook) {
    this.result = result;
    this.serverTime = serverTime;
    this.error = error;
    this.orderbook = orderbook;
  }

  public String getResult() {
    return result;
  }

  public String getServerTime() {
    return serverTime;
  }

  public String getError() {
    return error;
  }

  public KrakenDepth getOrderbook() {
    return orderbook;
  }
}
