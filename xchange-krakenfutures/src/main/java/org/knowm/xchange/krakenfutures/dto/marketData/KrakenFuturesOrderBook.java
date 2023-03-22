package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;
import org.knowm.xchange.instrument.Instrument;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesOrderBook extends KrakenFuturesResult {

  private final Date serverTime;
  private final KrakenFuturesBidsAsks bidsAsks;

  private Instrument instrument;

  public KrakenFuturesOrderBook(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("orderBook") KrakenFuturesBidsAsks bidsAsks) {

    super(result, error);
    this.serverTime = serverTime;
    this.bidsAsks = bidsAsks;
  }

  public void setInstrument(Instrument instrument){
    this.instrument = instrument;
  }
}
