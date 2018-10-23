package info.bitrich.xchangestream.bitmex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
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

    public BitmexStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
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
            BitmexWebSocketTransaction transaction = objectMapper.treeToValue(s, BitmexWebSocketTransaction.class);
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
        return objectMapper.writeValueAsString(subscribeMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("unsubscribe", new String[]{});
        return objectMapper.writeValueAsString(subscribeMessage);
    }
}
