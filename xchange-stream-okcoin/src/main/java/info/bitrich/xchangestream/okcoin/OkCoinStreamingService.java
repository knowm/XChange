package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.WebSocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkCoinStreamingService extends JsonNettyStreamingService {

  private Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

  private Disposable pingPongSubscription;

  public OkCoinStreamingService(String apiUrl) {
    super(apiUrl);
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
                pingPongSubscription =
                    pingPongSrc.subscribe(
                        o -> {
                          this.sendMessage("{\"event\":\"ping\"}");
                        });
                completable.onComplete();
              } catch (Exception e) {
                completable.onError(e);
              }
            });
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return message.get("channel").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    WebSocketMessage webSocketMessage = new WebSocketMessage("addChannel", channelName);

    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return objectMapper.writeValueAsString(webSocketMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    WebSocketMessage webSocketMessage = new WebSocketMessage("removeChannel", channelName);

    return objectMapper.writeValueAsString(webSocketMessage);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    if (message.get("event") != null && "pong".equals(message.get("event").asText())) {
      // ignore pong message
      return;
    }
    if (message.get("data") != null) {
      if (message.get("data").has("result")) {
        boolean success = message.get("data").get("result").asBoolean();
        if (!success) {
          super.handleError(
              message,
              new ExchangeException(
                  "Error code: " + message.get("data").get("error_code").asText()));
        }
        return;
      }
    }
    super.handleMessage(message);
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshaker,
      WebSocketClientHandler.WebSocketMessageHandler handler) {
    return new OkCoinNettyWebSocketClientHandler(handshaker, handler);
  }

  protected class OkCoinNettyWebSocketClientHandler extends NettyWebSocketClientHandler {

    private final Logger LOG = LoggerFactory.getLogger(OkCoinNettyWebSocketClientHandler.class);

    protected OkCoinNettyWebSocketClientHandler(
        WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
        pingPongSubscription.dispose();
      }
      super.channelInactive(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

      if (!handshaker.isHandshakeComplete()) {
        super.channelRead0(ctx, msg);
        return;
      }

      super.channelRead0(ctx, msg);

      WebSocketFrame frame = (WebSocketFrame) msg;
      if (frame instanceof BinaryWebSocketFrame) {
        BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
        ByteBuf byteBuf = binaryFrame.content();
        byte[] temp = new byte[byteBuf.readableBytes()];
        ByteBufInputStream bis = new ByteBufInputStream(byteBuf);
        StringBuilder appender = new StringBuilder();
        try {
          bis.read(temp);
          bis.close();
          Inflater infl = new Inflater(true);
          infl.setInput(temp, 0, temp.length);
          byte[] result = new byte[1024];
          while (!infl.finished()) {
            int length = infl.inflate(result);
            appender.append(new String(result, 0, length, "UTF-8"));
          }
          infl.end();
        } catch (Exception e) {
          LOG.trace("Error when inflate websocket binary message");
        }
        handler.onMessage(appender.toString());
      }
    }
  }
}
