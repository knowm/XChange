package info.bitrich.xchangestream.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BTCMarketsWebSocketSubscribeMessage {

  @JsonProperty("messageType")
  public final String messageType = "subscribe";

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

  /**
   * @param marketIds All market id's to subscribe on, any current subscriptions will be dropped if
   *     not in the current message.
   */
  public BTCMarketsWebSocketSubscribeMessage(
      List<String> marketIds, List<String> channels, Long timestamp, String key, String signature) {
    this.marketIds = marketIds;
    this.channels = channels;
    this.timestamp = timestamp;
    this.key = key;
    this.signature = signature;
  }
}
