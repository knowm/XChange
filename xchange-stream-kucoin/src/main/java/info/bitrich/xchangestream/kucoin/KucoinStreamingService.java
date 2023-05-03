package info.bitrich.xchangestream.kucoin;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketUnsubscribeMessage;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketSubscribeMessage;
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
import java.util.concurrent.atomic.AtomicLong;

class KucoinStreamingService extends JsonNettyStreamingService {

  private final AtomicLong refCount = new AtomicLong();
  private final Observable<Long> pingPongSrc;
  private final boolean privateChannel;
  private Disposable pingPongSubscription;

  public KucoinStreamingService(String apiUrl, int pingInterval, boolean privateChannel) {
    super(apiUrl);

    this.privateChannel = privateChannel;
    pingPongSrc = Observable.interval(pingInterval, pingInterval, TimeUnit.MILLISECONDS);
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
                        pingPongSubscription = pingPongSrc.subscribe(o -> this.sendMessage("{\"type\":\"ping\", \"id\": \"" + refCount.incrementAndGet() + "\"}"));
                        completable.onComplete();
                      } catch (Exception e) {
                        completable.onError(e);
                      }
                    });
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    JsonNode topic = message.get("topic");
    return topic != null ? topic.asText() : null;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    KucoinWebSocketSubscribeMessage message = new KucoinWebSocketSubscribeMessage(channelName, refCount.incrementAndGet(), privateChannel);
    return objectMapper.writeValueAsString(message);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    KucoinWebSocketUnsubscribeMessage message = new KucoinWebSocketUnsubscribeMessage(channelName, refCount.incrementAndGet());
    return objectMapper.writeValueAsString(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    JsonNode typeNode = message.get("type");
    if (typeNode != null) {
      String type = typeNode.asText();
      if ("message".equals(type))
        super.handleMessage(message);
      else if ("error".equals(type))
        super.handleError(message, new Exception(message.get("data").asText()));
    }
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketClientHandler.WebSocketMessageHandler handler) {
    return new KucoinNettyWebSocketClientHandler(handshaker, handler);
  }

  private class KucoinNettyWebSocketClientHandler extends NettyWebSocketClientHandler {
    public KucoinNettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    public void channelInactive(ChannelHandlerContext ctx) {
      if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
        pingPongSubscription.dispose();
      }
      super.channelInactive(ctx);
    }
  }
}
