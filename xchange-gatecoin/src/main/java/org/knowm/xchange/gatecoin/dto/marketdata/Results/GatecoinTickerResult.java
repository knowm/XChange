package org.knowm.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

/** @author sumdeha */
public class GatecoinTickerResult extends GatecoinResult {

  private final GatecoinTicker[] tickers;

  @JsonCreator
  public GatecoinTickerResult(
      @JsonProperty("tickers") GatecoinTicker[] tickers,
      @JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
    this.tickers = tickers;
  }

  public GatecoinTicker[] getTicker() {
    return tickers;
  }
}
