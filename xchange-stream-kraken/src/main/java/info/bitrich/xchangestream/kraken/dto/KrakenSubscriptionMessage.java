package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import java.util.List;

/** @author pchertalev */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenSubscriptionMessage extends KrakenEvent {

  /** Optional, client originated ID reflected in response message. */
  @JsonProperty private final Integer reqid;

  /**
   * Optional - Array of currency pairs. Format of each pair is "A/B", where A and B are ISO 4217-A3
   * for standardized assets and popular unique symbol if not standardized.
   */
  @JsonProperty(value = "pair", required = false)
  private final List<String> pairs;

  @JsonProperty("subscription")
  private final KrakenSubscriptionConfig subscription;

  @JsonCreator
  public KrakenSubscriptionMessage(
      @JsonProperty("reqid") Integer reqid,
      @JsonProperty("event") KrakenEventType event,
      @JsonProperty("pair") List<String> pairs,
      @JsonProperty("subscription") KrakenSubscriptionConfig subscription) {
    super(event);
    this.reqid = reqid;
    this.pairs = pairs;
    this.subscription = subscription;
  }

  public List<String> getPairs() {
    return pairs;
  }

  public KrakenSubscriptionConfig getSubscription() {
    return subscription;
  }

  public Integer getReqid() {
    return reqid;
  }
}
