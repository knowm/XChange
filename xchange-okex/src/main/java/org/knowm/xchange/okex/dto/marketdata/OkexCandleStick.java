package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class OkexCandleStick {
  @JsonProperty("ts")
  private final Long timestamp;
  @JsonProperty("o")
  private final String openPrice;
  @JsonProperty("c")
  private final String closePrice;
  @JsonProperty("h")
  private final String highPrice;
  @JsonProperty("l")
  private final String lowPrice;
  @JsonProperty("vol")
  private final String volume;
  @JsonProperty("volCcy")
  private final String volumeCcy;
  @JsonProperty("volCcyQuote")
  private final String volCcyQuote;
  @JsonProperty("confirm")
  private final String confirm;

  @JsonCreator
  public OkexCandleStick(JsonNode node) {
    this.timestamp = node.get(0).asLong();
    this.openPrice = node.get(1).asText();
    this.closePrice = node.get(4).asText();
    this.highPrice = node.get(2).asText();
    this.lowPrice = node.get(3).asText();
    this.volume = node.get(5).asText();
    this.volumeCcy = node.get(6).asText();
    this.volCcyQuote = node.get(7).asText();
    this.confirm = node.get(8).asText();
  }

}
