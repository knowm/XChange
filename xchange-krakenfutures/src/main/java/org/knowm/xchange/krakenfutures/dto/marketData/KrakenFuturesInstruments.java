package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Neil Panchen */
public class KrakenFuturesInstruments extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<KrakenFuturesInstrument> instruments;

  public KrakenFuturesInstruments(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("instruments") List<KrakenFuturesInstrument> instruments) {

    super(result, error);

    this.serverTime = serverTime;
    this.instruments = instruments;
  }

  public List<KrakenFuturesInstrument> getInstruments() {
    return instruments;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res =
          new StringBuilder(
              "KrakenFutureInstruments [serverTime=" + serverTime + ",instruments=");
      for (KrakenFuturesInstrument ct : instruments) res.append(ct.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
