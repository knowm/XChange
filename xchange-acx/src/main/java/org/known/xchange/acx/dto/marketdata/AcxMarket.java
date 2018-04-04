package org.known.xchange.acx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcxMarket {
  /** A timestamp in seconds since Epoch */
  public final long at;

  public final AcxTicker ticker;

  public AcxMarket(@JsonProperty("at") long at, @JsonProperty("ticker") AcxTicker ticker) {
    this.at = at;
    this.ticker = ticker;
  }
}
