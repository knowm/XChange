package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodTradingPairs {
  private final List<CobinhoodCurrencyPair> pairs;

  public CobinhoodTradingPairs(@JsonProperty("trading_pairs") List<CobinhoodCurrencyPair> pairs) {
    this.pairs = pairs;
  }

  public List<CobinhoodCurrencyPair> getPairs() {
    return pairs;
  }
}
