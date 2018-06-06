package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Observable;
import org.knowm.xchange.bitmex.service.BitmexDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexStreamingService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingService.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey;
    private final String secretKey;

    public BitmexStreamingService(String apiUrl, String apiKey, String secretKey) {
        super(apiUrl, Integer.MAX_VALUE);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

	 @Override
    protected void handleMessage(JsonNode message) {
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
    protected DefaultHttpHeaders getCustomHeaders() {
        DefaultHttpHeaders customHeaders = super.getCustomHeaders();
        if (secretKey == null || apiKey == null) {
            return customHeaders;
        }
        long expires = System.currentTimeMillis() / 1000 + 5;

        BitmexDigest bitmexDigester = BitmexDigest.createInstance(secretKey, apiKey);
        String stringToDigest = "GET/realtime" + expires;
        String signature = bitmexDigester.digestString(stringToDigest);

        customHeaders.add("api-nonce", expires);
        customHeaders.add("api-key", apiKey);
        customHeaders.add("api-signature", signature);
        return customHeaders;
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        JsonNode data = message.get("data");
        String instrument = data.size() > 0 ? data.get(0).get("symbol").asText() : message.get("filter").get("symbol").asText();
        String table = message.get("table").asText();
        return String.format("%s:%s", table, instrument);
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("subscribe", new String[]{channelName});
        return mapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("unsubscribe", new String[]{});
        return mapper.writeValueAsString(subscribeMessage);
    }
}
