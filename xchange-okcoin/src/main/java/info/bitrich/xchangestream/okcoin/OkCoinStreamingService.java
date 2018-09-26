package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.WebSocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkCoinStreamingService extends JsonNettyStreamingService {

    private Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    public OkCoinStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        return conn.andThen((CompletableSource)(completable) -> {
            try {
                if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                    pingPongSubscription.dispose();
                }
                pingPongSubscription = pingPongSrc.subscribe(o -> {
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

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(webSocketMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        WebSocketMessage webSocketMessage = new WebSocketMessage("removeChannel", channelName);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(webSocketMessage);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.get("event") != null && "pong".equals(message.get("event").asText()) ) {
            // ignore pong message
            return;
        }
        if (message.get("data") != null) {
            if (message.get("data").has("result")) {
                boolean success = message.get("data").get("result").asBoolean();
                if (!success) {
                    super.handleError(message, new ExchangeException("Error code: " + message.get("data").get("error_code").asText()));
                }
                return;
            }
        }
        super.handleMessage(message);
    }

    @Override
    protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketClientHandler.WebSocketMessageHandler handler) {
        return new OkCoinNettyWebSocketClientHandler(handshaker, handler);
    }

    protected class OkCoinNettyWebSocketClientHandler extends NettyWebSocketClientHandler {

        protected OkCoinNettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
            super(handshaker, handler);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
                pingPongSubscription.dispose();
            }
            super.channelInactive(ctx);
        }
    }
}
