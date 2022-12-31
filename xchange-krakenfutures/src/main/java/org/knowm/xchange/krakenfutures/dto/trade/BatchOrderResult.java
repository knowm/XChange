package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.Util;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

@Getter
@ToString
public class BatchOrderResult extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<BatchStatus> batchStatus;

  public BatchOrderResult(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("batchStatus") List<BatchStatus> batchStatus) {
    super(result, error);
    this.serverTime = Util.parseDate(strServerTime);
    this.batchStatus = batchStatus;
  }
}
