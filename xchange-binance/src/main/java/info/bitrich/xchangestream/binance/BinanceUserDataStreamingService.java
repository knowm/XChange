package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;

import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;

import io.reactivex.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class BinanceUserDataStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(BinanceUserDataStreamingService.class);

	private static final String USER_API_BASE_URI = "wss://stream.binance.com:9443/ws/";

    public static BinanceUserDataStreamingService create(String listenKey) {
        return new BinanceUserDataStreamingService(USER_API_BASE_URI + listenKey);
    }

    private BinanceUserDataStreamingService(String url) {
        // Binance sends a ping every 3 minutes, so it would take that long to tell if
        // the host is lost. During that time, we could lose connectivity, miss updates,
        // regain connectivity, receive a ping and have no idea we missed an update. As
        // a result, we will consider the connection "idle" if we don't receive anything
        // for 2.5 minutes. This means that in the absence of any "real" updates, we'll
        // force an API update once every 2.5 minutes.
        super(url, Integer.MAX_VALUE, Duration.ofSeconds(10), Duration.ofSeconds(15), 150);
    }

    public Observable<JsonNode> subscribeChannel(BinanceWebSocketTypes eventType) {
    	return super.subscribeChannel(eventType.getSerializedValue());
    }

    @Override
    public void messageHandler(String message) {
        LOG.debug("Received message: {}", message);
        super.messageHandler(message);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        try {
            super.handleMessage(message);
        } catch (Exception e) {
            LOG.error("Error handling message: " + message, e);
            return;
        }
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        return message.get("e").asText();
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        // No op. Disconnecting from the web socket will cancel subscriptions.
        return null;
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        // No op. Disconnecting from the web socket will cancel subscriptions.
        return null;
    }

    @Override
    public void sendMessage(String message) {
        // Subscriptions are made upon connection - no messages are sent.
    }
}
