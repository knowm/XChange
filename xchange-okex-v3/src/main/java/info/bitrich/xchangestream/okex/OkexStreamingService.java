package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import info.bitrich.xchangestream.okex.dto.RequestMessage;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

public class OkexStreamingService extends NettyStreamingService<JsonNode> {

    private final Logger LOG = LoggerFactory.getLogger(OkexStreamingService.class);

    private io.reactivex.Observable<Long> pingPongSrc = Observable.interval(15, 15, TimeUnit.SECONDS);

    private Disposable pingPongSubscription;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

    protected ExchangeSpecification exchangeSpecification;

    public OkexStreamingService(String apiUrl) {
        super(apiUrl);
    }

    public void setExchangeSpecification(ExchangeSpecification exchangeSpecification) {
        this.exchangeSpecification = exchangeSpecification;
    }

    @Override
    public Completable connect() {
        if (pingPongSubscription != null && !pingPongSubscription.isDisposed()) {
            pingPongSubscription.dispose();
        }
        Completable conn = super.connect().andThen((CompletableSource) (completable) -> {
            pingPongSubscription = pingPongSrc.subscribe(o -> this.sendMessage("ping"));
            completable.onComplete();
        });
        if (this.exchangeSpecification.getApiKey() == null) {
            return conn;
        }
        return conn.andThen((CompletableSource) (completable) -> {
            try {
                // login
                String apiKey = exchangeSpecification.getApiKey();
                String apiSecret = exchangeSpecification.getSecretKey();
                String passphrase;
                if (exchangeSpecification.getExchangeSpecificParametersItem("Passphrase") == null) {
                    passphrase = exchangeSpecification.getPassword();
                } else {
                    passphrase = exchangeSpecification.getExchangeSpecificParametersItem("Passphrase").toString();
                }
                sendMessage(objectMapper.writeValueAsString(OkexAuthenticator.authenticateMessage(apiKey, apiSecret, passphrase)));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                completable.onComplete();
            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
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
        RequestMessage requestMessage = new RequestMessage(RequestMessage.Operation.SUBSCRIBE, Collections.singletonList(channelName));
        return objectMapper.writeValueAsString(requestMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        RequestMessage requestMessage = new RequestMessage(RequestMessage.Operation.UNSUBSCRIBE, Collections.singletonList(channelName));
        return objectMapper.writeValueAsString(requestMessage);
    }

    @Override
    public void messageHandler(String message) {
        try {
            LOG.debug("Received message: {}", message);
            if ("pong".equals(message)) {
                // just ignore pong now
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode;

            // Parse incoming message to JSON
            try {
                jsonNode = objectMapper.readTree(message);
            } catch (IOException e) {
                LOG.error("Error parsing incoming message to JSON: {}", message);
                return;
            }

            // In case of array - handle every message separately.
            if (jsonNode.getNodeType().equals(JsonNodeType.ARRAY)) {
                for (JsonNode node : jsonNode) {
                    handleMessage(node);
                }
            } else {
                handleMessage(jsonNode);
            }
        } catch (Exception exception) {
            LOG.error("Error while handling message: {}, exception: {}", message, exception);
        }
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (message.has("event")) {
            if ("pong".equals(message.get("event").asText())) {
                return;
            }
            if ("error".equals(message.get("event").asText())) {
                LOG.error("Error Message Received: {}", message);
                return;
            }
            LOG.info("Event Message: {}", message);
        }
        if (message.get("table") != null && message.get("data") != null) {
            String table = message.get("table").asText();
            Set<String> instrumentIds = new HashSet<>();
            boolean delayEmitted = false;
            for (JsonNode data : message.get("data")) {
                if (data.get("instrument_id") != null) {
                    instrumentIds.add(data.get("instrument_id").asText());
                }
                if (data.get("timestamp") != null && !delayEmitted) {
                    delayEmitted = true;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    for (ObservableEmitter<Long> emitter : delayEmitters) {
                        try {
                            emitter.onNext(System.currentTimeMillis() - format.parse(data.get("timestamp").asText()).getTime());
                        } catch (ParseException e) {
                            LOG.error("Parse timestamp error", e);
                        }
                    }
                }
            }
            for (String instrumentId : instrumentIds) {
                handleChannelMessage(String.format("%s:%s", table, instrumentId), message);
            }
        }
    }

    public void addDelayEmitter(ObservableEmitter<Long> delayEmitter) {
        delayEmitters.add(delayEmitter);
    }

    @Override
    protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketClientHandler.WebSocketMessageHandler handler) {
        return new OkCoinNettyWebSocketClientHandler(handshaker, handler);
    }

    protected class OkCoinNettyWebSocketClientHandler extends NettyWebSocketClientHandler {

        private final Logger LOG = LoggerFactory.getLogger(OkCoinNettyWebSocketClientHandler.class);

        protected OkCoinNettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
            super(handshaker, handler);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
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
