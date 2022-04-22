package org.knowm.xchange.okex.v5.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/** https://www.okx.com/docs-v5/en/#rest-api-market-data-get-ticker * */
@Getter
@NoArgsConstructor
public class OkexTicker {
  @JsonProperty("instType")
  private String instrumentType;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("last")
  private String last;

  @JsonProperty("lastSz")
  private String lastSz;

  @JsonProperty("askPx")
  private String askPx;

  @JsonProperty("askSz")
  private String askSz;

  @JsonProperty("bidPx")
  private String bidPx;

  @JsonProperty("bidSz")
  private String bidSz;

  @JsonProperty("open24h")
  private String open24h;

  @JsonProperty("high24h")
  private String high24h;

  @JsonProperty("low24h")
  private String low24h;

  @JsonProperty("volCcy24h")
  private String volCcy24h;

  @JsonProperty("vol24h")
  private String vol24h;

  @JsonProperty("sodUtc0")
  private String sodUtc0;

  @JsonProperty("sodUtc8")
  private String sodUtc8;

  @JsonProperty("ts")
  private Date ts;
}
