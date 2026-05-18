package org.knowm.xchange.bybit.dto.marketdata.candles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BybitCandleStick {

  @JsonProperty("start")
  private long start;

  @JsonProperty("end")
  private long end;

  @JsonProperty("interval")
  private String interval;

  @JsonProperty("open")
  private String open;

  @JsonProperty("close")
  private String close;

  @JsonProperty("high")
  private String high;

  @JsonProperty("low")
  private String low;

  @JsonProperty("volume")
  private String volume;

  @JsonProperty("turnover")
  private String turnover;

  @JsonProperty("confirm")
  private boolean confirm;

  @JsonProperty("timestamp")
  private long timestamp;
}
