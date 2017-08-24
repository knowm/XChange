package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexTickersResponse {

  private final boolean success;
  private final String message;
  private final ArrayList<BittrexTicker> tickers;

  public BittrexTickersResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexTicker> result) {

    super();
    this.success = success;
    this.message = message;
    this.tickers = result;
  }

  public boolean isSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public ArrayList<BittrexTicker> getTickers() {

    return tickers;
  }

  @Override
  public String toString() {

    return "BittrexTickersResponse [success=" + success + ", message=" + message + ", tickers=" + tickers + "]";
  }

}
