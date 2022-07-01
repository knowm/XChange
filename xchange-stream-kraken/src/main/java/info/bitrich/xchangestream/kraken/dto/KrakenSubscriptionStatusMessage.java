package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionStatus;

/** @author pchertalev */
public class KrakenSubscriptionStatusMessage extends KrakenEvent {

  private final Integer channelID;
  private final Integer reqid;
  private final KrakenSubscriptionStatus status;
  private final String pair;
  private final KrakenSubscriptionConfig krakenSubscriptionConfig;
  private final String errorMessage;

  @JsonCreator
  public KrakenSubscriptionStatusMessage(
      @JsonProperty("event") KrakenEventType event,
      @JsonProperty("channelID") Integer channelID,
      @JsonProperty("reqid") Integer reqid,
      @JsonProperty("status") KrakenSubscriptionStatus status,
      @JsonProperty("pair") String pair,
      @JsonProperty("subscription") KrakenSubscriptionConfig krakenSubscriptionConfig,
      @JsonProperty("errorMessage") String errorMessage) {
    super(event);
    this.channelID = channelID;
    this.reqid = reqid;
    this.status = status;
    this.pair = pair;
    this.krakenSubscriptionConfig = krakenSubscriptionConfig;
    this.errorMessage = errorMessage;
  }

  public Integer getChannelID() {
    return channelID;
  }

  public KrakenSubscriptionStatus getStatus() {
    return status;
  }

  public String getPair() {
    return pair;
  }

  public KrakenSubscriptionConfig getKrakenSubscriptionConfig() {
    return krakenSubscriptionConfig;
  }

  public Integer getReqid() {
    return reqid;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
