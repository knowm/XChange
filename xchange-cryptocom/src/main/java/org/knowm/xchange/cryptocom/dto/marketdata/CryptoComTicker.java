package org.knowm.xchange.cryptocom.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComTicker {

  @JsonProperty("i")
  private String instrumentName;

  @JsonProperty("h")
  private String high24h;

  @JsonProperty("l")
  private String low24h;

  @JsonProperty("a")
  private String latestTradePrice;

  @JsonProperty("v")
  private String volume24h;

  @JsonProperty("vv")
  private String volume24hUsd;

  @JsonProperty("c")
  private String priceChange24h;

  @JsonProperty("b")
  private String bestBidPrice;

  @JsonProperty("bs")
  private String bestBidSize;

  @JsonProperty("k")
  private String bestAskPrice;

  @JsonProperty("ks")
  private String bestAskSize;

  @JsonProperty("t")
  private Long timestamp;

  @JsonProperty("oi")
  private String openInterest;
}
