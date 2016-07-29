package org.knowm.xchange.virtex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from VirtEx
 */
public final class VirtExTickerWrapper {

  private final VirtExTicker ticker;
  private final String status;
  private final String message;
  private final String apirate;

  /**
   * Constructor
   * 
   * @param ticker
   * @param status
   * @param message
   * @param apirate
   */
  public VirtExTickerWrapper(@JsonProperty("ticker") TickerWrapper ticker, @JsonProperty("message") String message,
      @JsonProperty("status") String status, @JsonProperty("apirate") String apirate) {

    this.ticker = ticker.getTicker();
    this.status = status;
    this.message = message;
    this.apirate = apirate;
  }

  public VirtExTicker getTicker() {

    return ticker;
  }

  public String getStatus() {

    return status;
  }

  public String getMessage() {

    return message;
  }

  public String getApiRate() {

    return apirate;
  }

  @Override
  public String toString() {

    return "VirtExTicker [ticker" + ticker + ", status=" + status + ", message=" + message + ", apirate=" + apirate + "]";

  }

}
