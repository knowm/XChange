package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxLoginMessage;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxStreamingAuthenticator;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkxStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(OkxStreamingService.class);
  private static final String LOGIN_SIGN_METHOD = "GET";
  private static final String LOGIN_SIGN_REQUEST_PATH = "/users/self/verify";

  private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);
  private WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler = null;
  private Disposable pingPongSubscription;

  private final ExchangeSpecification xSpec;

  public OkxStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
    super(apiUrl);
    this.xSpec = exchangeSpecification;
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshaker,
      WebSocketClientHandler.WebSocketMessageHandler handler) {
    LOG.info("Registering OkxWebSocketClientHandler");
    return new OkxWebSocketClientHandler(handshaker, handler);
  }

  public void setChannelInactiveHandler(
      WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    this.channelInactiveHandler = channelInactiveHandler;
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   */
  class OkxWebSocketClientHandler extends NettyWebSocketClientHandler {

    public OkxWebSocketClientHandler(
        WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      super.channelInactive(ctx);
      if (channelInactiveHandler != null) {
        channelInactiveHandler.onMessage("WebSocket Client disconnected!");
        LOG.info("channelInactive catch");
      }
    }
  }

  public void pingPongSubscriptionDispose() {
    if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
      pingPongSubscription.dispose();
    }
  }

  public void unsubscribeChannel(final String channelId, Object... args) {
    if (channels.remove(channelId) != null) {
      try {
        sendMessage(getUnsubscribeMessage(channelId, args));
      } catch (IOException e) {
        LOG.debug("Failed to unsubscribe channel: {} {}", channelId, e.toString());
      } catch (Exception e) {
        LOG.warn("Failed to unsubscribe channel: {}", channelId, e);
      }
    }
  }

  @Override
  public Completable connect() {
    Completable conn = super.connect();
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                  pingPongSubscription.dispose();
                }

                pingPongSubscription = pingPongSrc.subscribe(o -> this.sendMessage("ping"));
                completable.onComplete();
              } catch (Exception e) {
                completable.onError(e);
              }
            });
  }

  public void login() throws JsonProcessingException {
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    String sign =
        OkxStreamingAuthenticator.generateSignature(
            xSpec.getSecretKey(), timestamp, LOGIN_SIGN_METHOD, LOGIN_SIGN_REQUEST_PATH);
    if (sign == null) {
      LOG.error(String.format("Failed to get signature"));
      return;
    }

    OkxLoginMessage message = new OkxLoginMessage();
    String passphrase = (String) xSpec.getExchangeSpecificParametersItem("passphrase");
    OkxLoginMessage.LoginArg loginArg =
        new OkxLoginMessage.LoginArg(xSpec.getApiKey(), passphrase, timestamp, sign);
    message.getArgs().add(loginArg);

    this.sendMessage(objectMapper.writeValueAsString(message));
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    JsonNode jsonNode;

    // Parse incoming message to JSON
    try {
      jsonNode = objectMapper.readTree(message);
    } catch (IOException e) {
      if ("pong".equals(message)) {
        // ping pong message

        return;
      }
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }
    LOG.debug("messageHandler {} ", message);
    if (processArrayMessageSeparately() && jsonNode.isArray()) {
      // In case of array - handle every message separately.
      for (JsonNode node : jsonNode) {
        handleMessage(node);
      }
    } else {
      handleMessage(jsonNode);
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    if (message.has("event")) {
      String event = message.get("event").asText();
      if (event.equals("subscribe"))
        LOG.info(
            "Stream {} has been successfully subscribed to channel {}",
            message.get("arg").get("instId").asText(),
            message.get("arg").get("channel").asText());
      if (event.equals("error"))
        LOG.info(
            "Subscribe error code: {}, msg: {}",
            message.get("code").asText(),
            message.get("msg").asText());
      if (event.equals("unsubscribe"))
        LOG.info(
            "Stream {} has been successfully unsubscribed from channel {}",
            message.get("arg").get("instId").asText(),
            message.get("arg").get("channel").asText());
      return "";
    }
    JsonNode node = message.get("arg");
    return node.get("instId").asText() + "-" + node.get("channel").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length != 1) throw new IOException("SubscribeMessage: Insufficient arguments");

    return objectMapper.writeValueAsString(args[0]);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length != 1) throw new IOException("UnsubscribeMessage: Insufficient arguments");

    return objectMapper.writeValueAsString(args[0]);
  }
}
