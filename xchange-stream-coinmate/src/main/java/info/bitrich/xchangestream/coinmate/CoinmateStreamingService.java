package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;
import java.io.IOException;

/**
 * The new API uses one WebSocket connection per channel, unlike other exchanges, which use channels
 * over one WebSocket. This is a hack to use JsonNettyStreamingService as single channel source.
 */
class CoinmateStreamingService extends JsonNettyStreamingService {

  // this is not actually used in communication
  private final String channelName;

  CoinmateStreamingService(String url, String channelName) {
    super(url);
    this.channelName = channelName;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    return null;
  }

  public Observable<JsonNode> subscribeMessages() {
    return subscribeChannel(channelName)
        .filter(
            jsonNode -> "data".equals(jsonNode.get("event").asText()) && jsonNode.has("payload"))
        .map(jsonNode -> jsonNode.get("payload"))
        .share();
  }
}
