package org.knowm.xchange.bitcoinde.dto.marketdata;

import java.util.Arrays;

import org.knowm.xchange.bitcoinde.dto.BitcoindeResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeTradesWrapper extends BitcoindeResponse {

  private final BitcoindeTrade[] trades;

  public BitcoindeTradesWrapper(@JsonProperty("trades") BitcoindeTrade[] trades, @JsonProperty("credits") int credits, @JsonProperty("errors") String[]
      errors) {

    super(credits, errors);
    this.trades = trades;

  }

  public BitcoindeTrade[] getTrades() {
    return trades;
  }

  @Override
  public String toString() {
    return "BitcoindeTradesWrapper{" +
        "trades=" + Arrays.toString(trades) +
        "} " + super.toString();
  }
}
