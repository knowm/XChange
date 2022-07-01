package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateAuthenticedSubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmatePingMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateSubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateUnsubscribeMessage;
import info.bitrich.xchangestream.coinmate.v2.dto.auth.AuthParams;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Coinmate Websocket API 2.0 */
class CoinmateStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(CoinmateStreamingService.class);
  private final AuthParams authParams;

  CoinmateStreamingService(String url, AuthParams authParams) {
    super(url);
    this.authParams = authParams;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String event = message.get("event").asText();
    if (!"data".equals(event)) {
      return null;
    }
    return message.get("channel").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length > 0 && args[0].equals(true)) {
      return objectMapper.writeValueAsString(generateAuthenticatedSubscribeMessage(channelName));
    } else {
      return objectMapper.writeValueAsString(generateSubscribeMessage(channelName));
    }
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(generateUnsubscribeMessage(channelName));
  }

  @Override
  protected void handleIdle(ChannelHandlerContext ctx) {
    // the default zero frame is not handled by Coinmate API, use ping message instead
    try {
      ctx.writeAndFlush(
          new TextWebSocketFrame(objectMapper.writeValueAsString(new CoinmatePingMessage())));
    } catch (JsonProcessingException e) {
      LOG.error("Failed to write ping message.");
    }
  }

  private CoinmateSubscribeMessage generateSubscribeMessage(String channelName) {
    return new CoinmateSubscribeMessage(channelName);
  }

  private CoinmateAuthenticedSubscribeMessage generateAuthenticatedSubscribeMessage(
      String channelName) {
    return new CoinmateAuthenticedSubscribeMessage(channelName, authParams);
  }

  private CoinmateUnsubscribeMessage generateUnsubscribeMessage(String channelName) {
    return new CoinmateUnsubscribeMessage(channelName);
  }

  /** @return Client ID needed for private channel */
  String getUserId() {
    return authParams.getUserId();
  }
}
