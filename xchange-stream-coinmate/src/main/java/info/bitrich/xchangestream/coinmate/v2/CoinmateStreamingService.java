package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateAuthenticedSubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateSubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateUnsubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.auth.AuthParams;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;

import java.io.IOException;

/**
 * Coinmate Websocket API 2.0
 */
class CoinmateStreamingService extends JsonNettyStreamingService {

  private final AuthParams authParams;

  CoinmateStreamingService(String url, AuthParams authParams) {
    super(url);
    this.authParams = authParams;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String event = message.get("event").asText();
    if (!event.equals("data")) {
      return null;
    }
    return message.get("channel").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length > 0 && args[0].equals(true)) {
      // needs authentication
      CoinmateAuthenticedSubscribeMessage subscribeMessage =
          generateAuthenticatedSubscribeMessage(channelName);
      return objectMapper.writeValueAsString(subscribeMessage);
    } else {
      CoinmateSubscribeMessage subscribeMessage =
          generateSubscribeMessage(channelName);
      return objectMapper.writeValueAsString(subscribeMessage);
    }
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    CoinmateUnsubscribeMessage unsubscribeMessage =
        generateUnsubscribeMessage(channelName);
    return objectMapper.writeValueAsString(unsubscribeMessage);
  }

  private CoinmateSubscribeMessage generateSubscribeMessage(String channelName) {
    return new CoinmateSubscribeMessage(channelName);
  }

  private CoinmateAuthenticedSubscribeMessage generateAuthenticatedSubscribeMessage(String channelName) {
    return new CoinmateAuthenticedSubscribeMessage(channelName, authParams);
  }

  private CoinmateUnsubscribeMessage generateUnsubscribeMessage(String channelName) {
    return new CoinmateUnsubscribeMessage(channelName);
  }

  /**
   *
   * @return Client ID needed for private channel
   */
  String getUserId() {
    return authParams.getUserId();
  }
}
