package info.bitrich.xchangestream.coincheck;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coincheck.dto.CoincheckSubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import java.io.IOException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoincheckStreamingService extends JsonNettyStreamingService {

  public CoincheckStreamingService(String baseUri) {
    super(baseUri, Integer.MAX_VALUE);
  }

  public CoincheckStreamingService(
      String baseUri,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds) {
    super(baseUri, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    super.handleMessage(message);
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    CoincheckSubscribeMessage msg = new CoincheckSubscribeMessage("subscribe", channelName);
    return objectMapper.writeValueAsString(msg);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    // Coincheck does not support unsubscribes.
    return null;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }

  @Override
  public boolean processArrayMessageSeparately() {
    return false;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return CoincheckStreamingAdapter.getChannelNameFromMessage(message);
  }
}
