package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

@Getter
public class KrakenFuturesCancelAllOrdersAfter extends KrakenFuturesResult {

  private final Date serverTime;
  private final Status status;

  public KrakenFuturesCancelAllOrdersAfter(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("status") Status status,
      @JsonProperty("error") String error) {

    super(result, error);
    this.serverTime = serverTime;
    this.status = status;
  }

  @Getter
  public static class Status {
    private Date currentTime;
    private Date triggerTime;
  }
}
