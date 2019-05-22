package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okex.dto.RequestMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableEmitter;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.Inflater;

public class OkexStreamingService extends JsonNettyStreamingService {

    private final Logger LOG = LoggerFactory.getLogger(OkexStreamingService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

    protected ExchangeSpecification exchangeSpecification;

    private ConcurrentHashMap<String, JsonNode> eventResponse = new ConcurrentHashMap<>();

    public OkexStreamingService(String apiUrl) {
        super(apiUrl);
    }

    public void setExchangeSpecification(ExchangeSpecification exchangeSpecification) {
        this.exchangeSpecification = exchangeSpecification;
    }

    @Override
    public Completable connect() {
        Completable conn = super.connect();
        if (this.exchangeSpecification.getApiKey() == null) {
            return conn;
        }
        return conn.andThen((CompletableSource) (completable) -> {
            try {
                String apiKey = exchangeSpecification.getApiKey();
                String apiSecret = exchangeSpecification.getSecretKey();
                String passphrase;
                if (exchangeSpecification.getExchangeSpecificParametersItem("Passphrase") == null) {
                    passphrase = exchangeSpecification.getPassword();
                } else {
                    passphrase = exchangeSpecification.getExchangeSpecificParametersItem("Passphrase").toString();
                }
                eventResponse.remove("error");
                eventResponse.remove("login");
                sendMessage(objectMapper.writeValueAsString(OkexAuthenticator.authenticateMessage(apiKey, apiSecret, passphrase)));
                while (!eventResponse.containsKey("error") && !eventResponse.containsKey("login")) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
                if (eventResponse.containsKey("login")) {
                    completable.onComplete();
                } else {
                    completable.onError(new Exception(eventResponse.get("error").asText()));
                }
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
    protected void handleMessage(JsonNode message) {
        if (message.has("event")) {
            eventResponse.put(message.get("event").asText(), message);
            if ("error".equals(message.get("event").asText())) {
                LOG.error("Error Message Received: {}", message);
                return;
            }
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
