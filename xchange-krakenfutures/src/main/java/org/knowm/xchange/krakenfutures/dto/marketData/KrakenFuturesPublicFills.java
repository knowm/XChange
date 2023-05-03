package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;
import org.knowm.xchange.instrument.Instrument;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesPublicFills extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<KrakenFuturesPublicFill> fills;
  private Instrument instrument;

  public KrakenFuturesPublicFills(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("history") List<KrakenFuturesPublicFill> fills) {

    super(result, error);

    this.serverTime = serverTime;
    this.fills = fills;
  }

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

}
