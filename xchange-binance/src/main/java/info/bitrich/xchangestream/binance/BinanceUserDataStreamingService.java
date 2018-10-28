package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;

import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;

import java.io.IOException;

import org.knowm.xchange.binance.BinanceAuthenticated;

public class BinanceUserDataStreamingService extends JsonNettyStreamingService {

    private static final String SUBSCRIBE = "SUBSCRIBE";
	  private static final String USER_API_BASE_URI = "wss://stream.binance.com:9443/ws/";

    private final BinanceAuthenticated binance;
    private final String apiKey;
    private final String listenKey;

    public static BinanceUserDataStreamingService create(BinanceAuthenticated binance, String apiKey) {
        return new BinanceUserDataStreamingService(binance, apiKey, userChannel(binance, apiKey));
    }

    private BinanceUserDataStreamingService(BinanceAuthenticated binance, String apiKey, String listenKey) {
        super(USER_API_BASE_URI + listenKey, Integer.MAX_VALUE);
        this.binance = binance;
        this.apiKey = apiKey;
        this.listenKey = listenKey;
    }

    public Observable<JsonNode> subscribeChannel(BinanceWebSocketTypes eventType) {
    	return super.subscribeChannel(eventType.getSerializedValue());
    }

    @Override
    public void messageHandler(String message) {
        super.messageHandler(message);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        super.handleMessage(message);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        return message.get("e").asText();
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return SUBSCRIBE;
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        // No op. Disconnecting from the web socket will cancel subscriptions.
        return null;
    }

    @Override
    public void sendMessage(String message) {
        if (SUBSCRIBE.equals(message)) {
            try {
                binance.keepAliveUserDataStream(apiKey, listenKey);
            } catch (IOException e) {
                throw new RuntimeException("Failed to keep user stream alive", e);
            }
        }
    }

    private static String userChannel(BinanceAuthenticated binance, String apiKey) {
        try {
          return binance.startUserDataStream(apiKey).getListenKey();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
    }
}
