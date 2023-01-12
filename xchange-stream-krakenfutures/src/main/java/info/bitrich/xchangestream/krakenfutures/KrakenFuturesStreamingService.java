package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingAuthenticatedWebsocketMessage;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingChallengeRequest;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingWebsocketMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class KrakenFuturesStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingService.class);

    private final String SUBSCRIBE = "subscribe";

    protected final String ORDERBOOK = "book";
    protected final String TICKER = "ticker";
    protected final String TRADES = "trade";
    protected final String FILLS = "fills";

    private String CHALLENGE = "";
    private final ExchangeSpecification exchangeSpecification;
    public KrakenFuturesStreamingService(String apiUrl, ExchangeSpecification exchangeSpecification) {
        super(apiUrl);
        this.exchangeSpecification = exchangeSpecification;
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
        String channelName = "";

        if(message.has("event")){
            if(message.get("event").asText().equals("info")){
                if(exchangeSpecification.getApiKey() != null){
                    try{
                        sendMessage(StreamingObjectMapperHelper.getObjectMapper().writeValueAsString(new KrakenFuturesStreamingChallengeRequest(exchangeSpecification.getApiKey())));
                    } catch (JsonProcessingException e){
                        LOG.error(e.getMessage());
                    }
                }
            }
        }

        if(message.has("event") && message.has("message")){
            if(message.get("event").asText().equals("challenge")){
                CHALLENGE = message.get("message").asText();
            }
        }

        if(message.has("feed") && message.has("product_id")){
            if(message.get("feed").asText().contains(ORDERBOOK)){
                channelName = ORDERBOOK+message.get("product_id").asText().toLowerCase();
            } else if(message.get("feed").asText().contains(TICKER)){
                channelName = TICKER+message.get("product_id").asText().toLowerCase();
            } else if(message.get("feed").asText().contains(TRADES)){
                channelName = TRADES+message.get("product_id").asText().toLowerCase();
            }
        }
        // Fills
        if(message.has("feed")){
            if(message.get("feed").asText().equals(FILLS)){
                channelName = FILLS;
            }
        }

        LOG.debug("ChannelName: "+channelName);
        return channelName;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if(channelName.equals(FILLS)){
            do{
                LOG.info("Waiting for challenge to complete...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (CHALLENGE.equals(""));
        }
        return objectMapper.writeValueAsString(getWebSocketMessage(SUBSCRIBE, channelName));
    }

    @Override
    public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
        String UNSUBSCRIBE = "unsubscribe";
        return objectMapper.writeValueAsString(getWebSocketMessage(UNSUBSCRIBE, channelName));
    }

    @Override
    public void setBeforeConnectionHandler(Runnable beforeConnectionHandler) {
        super.setBeforeConnectionHandler(beforeConnectionHandler);
        if (exchangeSpecification.getApiKey() != null) {
            LOG.info("Reset challenge string.");
            CHALLENGE = "";
        }
    }

    private KrakenFuturesStreamingWebsocketMessage getWebSocketMessage(String event, String channelName){
        if(channelName.contains(ORDERBOOK)){
            return new KrakenFuturesStreamingWebsocketMessage(event, ORDERBOOK, new String[]{channelName.replace(ORDERBOOK, "")});
        } else if(channelName.contains(TICKER)){
            return new KrakenFuturesStreamingWebsocketMessage(event, TICKER, new String[]{channelName.replace(TICKER, "")});
        } else if(channelName.contains(TRADES)){
            return new KrakenFuturesStreamingWebsocketMessage(event, TRADES, new String[]{channelName.replace(TRADES, "")});
        } else if(channelName.contains(FILLS)){
            if(event.equals(SUBSCRIBE)){
                return new KrakenFuturesStreamingAuthenticatedWebsocketMessage(
                        event,
                        FILLS,
                        null,
                        exchangeSpecification.getApiKey(),
                        CHALLENGE,
                        signChallenge()
                );
            } else {
                return new KrakenFuturesStreamingWebsocketMessage(event, FILLS, null);
            }
        } else {
            throw new NotImplementedException("ChangeName "+channelName+" has not been implemented yet.");
        }
    }

    private String signChallenge(){
        return KrakenFuturesDigest.createInstance(exchangeSpecification.getSecretKey()).signMessage(CHALLENGE);
    }
}
