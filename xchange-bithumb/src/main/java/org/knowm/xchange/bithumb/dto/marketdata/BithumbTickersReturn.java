package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(using = BithumbTickersDeserializer.class)
public class BithumbTickersReturn {

  private final Map<String, BithumbTicker> tickers;

  public BithumbTickersReturn(Map<String, BithumbTicker> tickers) {
    this.tickers = tickers;
  }

  public Map<String, BithumbTicker> getTickers() {
    return tickers;
  }

  @Override
  public String toString() {
    return "BithumbTickersReturn{" + "tickers=" + tickers + '}';
  }
}
