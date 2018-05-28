package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingService.class);
    protected final ObjectMapper mapper = new ObjectMapper();
    private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

    protected ExchangeSpecification exchangeSpecification;

    public BitmexStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void setExchangeSpecification(ExchangeSpecification exchangeSpecification) {
        this.exchangeSpecification = exchangeSpecification;
    }

    private void login() throws JsonProcessingException {
        long expires = System.currentTimeMillis() + 30;
        String apiKey = this.exchangeSpecification.getApiKey();
        String apiSecret = this.exchangeSpecification.getSecretKey();
        String path = "/realtime";
        String signature = BitmexAuthenticator.generateSignature(apiSecret,
                "GET", path, String.valueOf(expires), "");

        List<Object> args = Arrays.asList(apiKey, expires, signature);

        Map<String, Object> cmd = new HashMap<>();
        cmd.put("op", "authKey");
        cmd.put("args", args);
        this.sendMessage(mapper.writeValueAsString(cmd));
    }

    @Override
    public Completable connect() {
        // Note that we must override connect method in streaming service instead of streaming exchange, because of the auto reconnect feature of NettyStreamingService.
        // We must ensure the authentication message is also resend when the connection is rebuilt.
        Completable conn = super.connect();
        if (this.exchangeSpecification.getApiKey() == null) {
            return conn;
        }
        return conn.andThen((CompletableSource) (completable) -> {
            try {
                login();
                completable.onComplete();
            } catch (IOException e) {
                completable.onError(e);
            }
        });
    }

    @Override
    protected void handleMessage(JsonNode message) {
        if (!delayEmitters.isEmpty() && message.has("data")) {
            JsonNode data = message.get("data");
            if (data.getNodeType().equals(JsonNodeType.ARRAY)) {
                Long current = System.currentTimeMillis();
                SimpleDateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                for (JsonNode d : data) {
                    if (d.has("timestamp")) {
                        try {
                            String timestamp = d.get("timestamp").asText();
                            Date date = formatter.parse(timestamp);
                            for (ObservableEmitter<Long> emitter : delayEmitters) {
                                emitter.onNext(current - date.getTime());
                            }
                        } catch (ParseException e) {
                            LOG.error("Parsing timestamp error: ", e);
                        }
                    }
                }
            }
        }
        if (message.has("info") || message.has("success")) {
            return;
        }
        if (message.has("error")) {
            String error = message.get("error").asText();
            LOG.error("Error with message: " + error);
            return;
        }

        super.handleMessage(message);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }

    public Observable<BitmexWebSocketTransaction> subscribeBitmexChannel(String channelName) {
        return subscribeChannel(channelName).map(s -> {
            BitmexWebSocketTransaction transaction = mapper.readValue(s.toString(), BitmexWebSocketTransaction.class);
            return transaction;
        })
                .share();
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String instrument = message.get("data").get(0).get("symbol").asText();
        String table = message.get("table").asText();
        return String.format("%s:%s", table, instrument);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("subscribe", new String[]{channelName});
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("unsubscribe", new String[]{});
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    public void addDelayEmitter(ObservableEmitter<Long> delayEmitter) {
        delayEmitters.add(delayEmitter);
    }

}
