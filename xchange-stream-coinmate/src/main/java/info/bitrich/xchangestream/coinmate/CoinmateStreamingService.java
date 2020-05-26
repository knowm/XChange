package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coinmate.dto.auth.AuthParams;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import sun.rmi.runtime.Log;

import java.io.IOException;

/**
 * The new API uses one WebSocket connection per channel, unlike other exchanges,
 * which use channels over one WebSocket. This is a hack to use JsonNettyStreamingService as
 * single channel source.
 */
public class CoinmateStreamingService extends JsonNettyStreamingService {

  private PublishSubject<JsonNode> messages;

  CoinmateStreamingService(String url) {
    super(url);
    this.messages = PublishSubject.create();
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return null;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    return null;
  }

  @Override
  protected void handleMessage(JsonNode message) {
    this.messages.onNext(message);
  }

  public Observable<JsonNode> subscribeMessages() {
    return messages.filter(jsonNode -> {
      return "data".equals(jsonNode.get("event").asText()) && jsonNode.has("payload");
    }).map(jsonNode -> {
      return jsonNode.get("payload");
    }).share();
  }
}
