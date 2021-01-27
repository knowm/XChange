package info.bitrich.xchangestream.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BTCMarketsWebSocketSubscriptionMessage {

  @JsonProperty("messageType")
  public final String messageType;

  @JsonProperty("marketIds")
  public final List<String> marketIds;

  @JsonProperty("channels")
  public final List<String> channels;

  @JsonProperty("timestamp")
  public final Long timestamp;

  @JsonProperty("key")
  public final String key;

  @JsonProperty("signature")
  public final String signature;

  @JsonProperty("clientType")
  public final String clientType;

  /**
   * Use the static method {@link BTCMarketsWebSocketSubscriptionMessage.getFirstSubcritionMessage}
   * instead.
   *
   * @param marketIds All market id's to subscribe on, any current subscriptions will be dropped if
   *     not in the current message.
   * @implNote kept for backward compatibility
   */
  @Deprecated
  public BTCMarketsWebSocketSubscriptionMessage(
      List<String> marketIds, List<String> channels, Long timestamp, String key, String signature) {
    this.messageType = "subscribe";
    this.marketIds = marketIds;
    this.channels = channels;
    this.timestamp = timestamp;
    this.key = key;
    this.signature = signature;
    this.clientType = null;
  }

  private BTCMarketsWebSocketSubscriptionMessage(
      String messageType,
      List<String> marketIds,
      List<String> channels,
      Long timestamp,
      String key,
      String signature,
      String clientType) {
    this.messageType = messageType;
    this.marketIds = marketIds;
    this.channels = channels;
    this.timestamp = timestamp;
    this.key = key;
    this.signature = signature;
    this.clientType = clientType;
  }

  /**
   * Use this method to retrieve a subscribe message when you intend to add or remove subscriptions
   * at a later stage All other existing subscriptions will be removed.
   *
   * @param marketIds
   * @param channels
   * @param timestamp
   * @param key
   * @param signature
   * @return {@link BTCMarketsWebSocketSubscriptionMessage}
   */
  public static BTCMarketsWebSocketSubscriptionMessage getFirstSubscriptionMessage(
      List<String> marketIds, List<String> channels, Long timestamp, String key, String signature) {
    return new BTCMarketsWebSocketSubscriptionMessage(
        "subscribe", marketIds, channels, timestamp, key, signature, "api");
  }

  public static BTCMarketsWebSocketSubscriptionMessage getAddSubscriptionMessage(
      List<String> marketIds, List<String> channels, Long timestamp, String key, String signature) {
    return new BTCMarketsWebSocketSubscriptionMessage(
        "addSubscription", marketIds, channels, timestamp, key, signature, "api");
  }

  public static BTCMarketsWebSocketSubscriptionMessage getRemoveSubcriptionMessage(
      List<String> marketIds, List<String> channels, Long timestamp, String key, String signature) {
    return new BTCMarketsWebSocketSubscriptionMessage(
        "removeSubscription", marketIds, channels, timestamp, key, signature, "api");
  }
}
