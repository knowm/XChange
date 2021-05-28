package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceUserDataStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceUserDataStreamingService.class);

  private static final String USER_FUTURES_API_BASE_URI = "wss://fstream.binance.com/ws/";
  private static final String USER_FUTURES_SANDBOX_URI = "wss://testnet.binancefuture.com/ws/";
  private static final String USER_API_BASE_URI = "wss://stream.binance.com:9443/ws/";
  private static final String USER_API_SANDBOX_URI = "wss://testnet.binance.vision/ws/";

  public static BinanceUserDataStreamingService create(String listenKey, boolean useSandbox) {
    return create(listenKey, useSandbox, false);
  }

  public static BinanceUserDataStreamingService create(String listenKey, boolean useSandbox, boolean useFutures) {
    return new BinanceUserDataStreamingService(baseUri(useSandbox, useFutures) + listenKey);
  }

  private static String baseUri(boolean useSandbox, boolean useFutures) {
    if (useFutures) {
      return useSandbox ? USER_FUTURES_SANDBOX_URI : USER_FUTURES_API_BASE_URI;
    } else {
      return useSandbox ? USER_API_SANDBOX_URI : USER_API_BASE_URI;
    }
  }

  private BinanceUserDataStreamingService(String url) {
    super(url, Integer.MAX_VALUE);
  }

  public Observable<JsonNode> subscribeChannel(BinanceWebSocketTypes eventType) {
    return super.subscribeChannel(eventType.getSerializedValue());
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    super.messageHandler(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    try {
      super.handleMessage(message);
    } catch (Exception e) {
      LOG.error("Error handling message: " + message, e);
      return;
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return message.get("e").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextAndServerNoContextHandler.INSTANCE;
  }

  @Override
  public void sendMessage(String message) {
    // Subscriptions are made upon connection - no messages are sent.
  }
}
