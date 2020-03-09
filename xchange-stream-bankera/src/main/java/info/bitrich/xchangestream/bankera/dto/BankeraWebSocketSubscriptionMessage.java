package info.bitrich.xchangestream.bankera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraWebSocketSubscriptionMessage {

  @JsonProperty("e")
  private String event;

  @JsonProperty("marketId")
  private String marketId;

  @JsonProperty("chartInterval")
  private String chartInterval;

  public BankeraWebSocketSubscriptionMessage(String marketId) {
    this.event = "market";
    this.marketId = marketId;
    this.chartInterval = "1m";
  }

  public BankeraWebSocketSubscriptionMessage(String marketId, String chartInterval) {
    this.event = "market";
    this.marketId = marketId;
    this.chartInterval = chartInterval;
  }

  public String getEvent() {
    return event;
  }

  public String getMarketId() {
    return marketId;
  }

  public String getChartInterval() {
    return chartInterval;
  }
}
