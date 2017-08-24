package org.knowm.xchange.bittrex.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexTickerResponse {

  private final boolean success;
  private final String message;
  private final BittrexTicker ticker;

  public BittrexTickerResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") ArrayList<BittrexTicker> result) {

    super();
    this.success = success;
    this.message = message;
    this.ticker = result.get(0);
  }

  public boolean getSuccess() {

    return success;
  }

  public String getMessage() {

    return message;
  }

  public BittrexTicker getTicker() {

    return ticker;
  }

  @Override
  public String toString() {

    return "BittrexTickerResponse [success=" + success + ", message=" + message + ", ticker=" + ticker + "]";
  }

}
