package org.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class BitZTickerAll {

  private final Map<String, BitZTicker> tickers;

  @JsonCreator
  public BitZTickerAll(Map<String, BitZTicker> tickers) {
    this.tickers = tickers;
  }

  public Map<String, BitZTicker> getAllTickers() {
    return tickers;
  }

  public BitZTicker getTicker(String pair) {
    return tickers.get(pair);
  }
}
