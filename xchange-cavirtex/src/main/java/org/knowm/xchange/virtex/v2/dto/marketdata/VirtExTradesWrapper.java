package org.knowm.xchange.virtex.v2.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class VirtExTradesWrapper {

  private final ArrayList<VirtExTrade> trades;
  private final String status;
  private final String message;
  private final String apirate;

  /**
   * Constructor
   * 
   * @param trades
   * @param status
   * @param message
   * @param apirate
   */
  public VirtExTradesWrapper(@JsonProperty("orders") ArrayList<VirtExTrade> trades, @JsonProperty("message") String message,
      @JsonProperty("status") String status, @JsonProperty("apirate") String apirate) {

    this.trades = trades;
    this.status = status;
    this.message = message;
    this.apirate = apirate;
  }

  public ArrayList<VirtExTrade> getTrades() {

    return trades;
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

    return "VirtExTrades [trades" + trades + ", status=" + status + ", message=" + message + ", apirate=" + apirate + "]";

  }

}
