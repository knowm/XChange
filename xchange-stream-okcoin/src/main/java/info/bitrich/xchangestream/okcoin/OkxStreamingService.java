package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkxStreamingService extends JsonNettyStreamingService {

    private final Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    public OkxStreamingService(String apiUrl) {
        super(apiUrl);
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
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        return null;
    }

    @Override
    public Completable connect() {
        return super.connect();
    }

    @Override
    protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketClientHandler.WebSocketMessageHandler handler) {
        return super.getWebSocketClientHandler(handshaker, handler);
    }

    protected class OkxNettyWebSocketClientHandler extends NettyWebSocketClientHandler {

        protected OkxNettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
            super(handshaker, handler);
        }
    }
}
