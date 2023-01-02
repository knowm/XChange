package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingWebsocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KrakenFuturesStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingService.class);

    private final String SUBSCRIBE = "subscribe";
    private final String UNSUBSCRIBE = "unsubscribe";

    protected final String ORDERBOOK = "book";
    protected final String TICKER = "ticker";
    protected final String TRADES = "trade";

    public KrakenFuturesStreamingService(String apiUrl) {
        super(apiUrl);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String channelName = "";

        if(message.has("feed") && message.has("product_id")){
            if(message.get("feed").asText().contains(ORDERBOOK)){
                channelName = ORDERBOOK+message.get("product_id").asText().toLowerCase();
            } else if(message.get("feed").asText().contains(TICKER)){
                channelName = TICKER+message.get("product_id").asText().toLowerCase();
            } else if(message.get("feed").asText().contains(TRADES)){
                channelName = TRADES+message.get("product_id").asText().toLowerCase();
            }
        }
        LOG.debug("ChannelName: "+channelName);
        return channelName;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return objectMapper.writeValueAsString(getWebSocketMessage(SUBSCRIBE, channelName));
    }

    @Override
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        return objectMapper.writeValueAsString(getWebSocketMessage(UNSUBSCRIBE, channelName));
    }

    private KrakenFuturesStreamingWebsocketMessage getWebSocketMessage(String event, String channelName){
        if(channelName.contains(ORDERBOOK)){
            return new KrakenFuturesStreamingWebsocketMessage(event, ORDERBOOK, new String[]{channelName.replace(ORDERBOOK, "")});
        } else if(channelName.contains(TICKER)){
            return new KrakenFuturesStreamingWebsocketMessage(event, TICKER, new String[]{channelName.replace(TICKER, "")});
        } else if(channelName.contains(TRADES)){
            return new KrakenFuturesStreamingWebsocketMessage(event, TRADES, new String[]{channelName.replace(TRADES, "")});
        } else {
            throw new NotImplementedException("ChangeName "+channelName+" has not been implemented yet.");
        }
    }
}
