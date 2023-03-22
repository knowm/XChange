package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Neil Panchen */
@Getter
@ToString
public class KrakenFuturesTickers extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<KrakenFuturesTicker> tickers;

  public KrakenFuturesTickers(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("tickers") List<KrakenFuturesTicker> tickers) {

    super(result, error);

    this.serverTime = serverTime;
    this.tickers = tickers;
  }

  public KrakenFuturesTicker getTicker(String symbol) {
    if (isSuccess() && tickers != null) {
      for (KrakenFuturesTicker ticker : tickers) {
        if (ticker != null && ticker.getSymbol().equalsIgnoreCase(symbol)) {
          return ticker;
        }
      }
    }
    return null;
  }
}
