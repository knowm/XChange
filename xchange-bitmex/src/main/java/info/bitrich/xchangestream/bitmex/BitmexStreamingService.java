package info.bitrich.xchangestream.bitmex;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.node.JsonNodeType;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.reactivex.ObservableEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingService.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

    public BitmexStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
