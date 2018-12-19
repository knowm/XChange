package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodTickers {
  private final List<CobinhoodTicker> tickers;

  public CobinhoodTickers(@JsonProperty("tickers") List<CobinhoodTicker> tickers) {
    this.tickers = tickers;
  }

  public List<CobinhoodTicker> getTickers() {
    return tickers;
  }
}
