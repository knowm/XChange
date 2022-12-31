package org.knowm.xchange.krakenfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.Util;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesFills extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<KrakenFuturesFill> fills;

  public KrakenFuturesFills(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("fills") List<KrakenFuturesFill> fills) {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
    this.fills = fills;
  }
}
