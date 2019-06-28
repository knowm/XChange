package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionConfig;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionMessage;
import info.bitrich.xchangestream.kraken.dto.KrakenSubscriptionStatusMessage;
import info.bitrich.xchangestream.kraken.dto.KrakenSystemStatus;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType.subscribe;

/**
 * @author makarid, pchertalev
 */
public class KrakenStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingService.class);
    private static final String EVENT = "event";
    private final Map<Integer, String> channels = new ConcurrentHashMap<>();
    private ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    private final Map<Integer, String> subscriptionRequestMap = new ConcurrentHashMap<>();

    public KrakenStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    public boolean processArrayMassageSeparately() {
        return false;
    }

    @Override
    protected void handleMessage(JsonNode message) {
        String channelName = getChannel(message);

        try {
            JsonNode event = message.get(EVENT);
            KrakenEventType krakenEvent;
            if (event != null && (krakenEvent = KrakenEventType.getEvent(event.textValue())) != null) {
                switch (krakenEvent) {
                    case heartbeat:
                        LOG.debug("Heartbeat received");
                        break;
                    case systemStatus:
                        KrakenSystemStatus systemStatus = mapper.treeToValue(message, KrakenSystemStatus.class);
                        LOG.info("System status: {}", systemStatus);
                        break;
                    case subscriptionStatus:
                        KrakenSubscriptionStatusMessage statusMessage = mapper.treeToValue(message, KrakenSubscriptionStatusMessage.class);
                        channelName = subscriptionRequestMap.remove(statusMessage.getReqid());
                        switch (statusMessage.getStatus()) {
                            case subscribed:
                                LOG.info("Channel {} has been subscribed", channelName);
                                channels.put(statusMessage.getChannelID(), channelName);
                                break;
                            case unsubscribed:
                                LOG.info("Channel {} has been unsubscribed", channelName);
                                channels.remove(statusMessage.getChannelID());
                                break;
                            case error:
                                LOG.error("Channel {} has been failed: {}", channelName, statusMessage.getErrorMessage());
                        }
                        break;
                    default:
                        LOG.warn("Unexpected event type has been received: {}", krakenEvent);
                }
                return;

            }
        } catch (JsonProcessingException e) {
            LOG.error("Error reading message: {}", e.getMessage(), e);
        }

        if (!message.isArray() || channelName == null) {
            LOG.error("Unknown message: {}", message.asText());
            return;
        }

        super.handleMessage(message);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        String channelName = null;
        if (message.has("channelID")) {
            channelName = channels.get(message.get("channelID").asInt());
        }

        if (message.isArray() && message.get(0).isInt()) {
            channelName = channels.get(message.get(0).asInt());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("ChannelName {}", StringUtils.isBlank(channelName) ? "not defined" : channelName);
        }
        return channelName;
    }


    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        String [] channelData = channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);
        String pair = channelData[1];
        KrakenSubscriptionName subscriptionName = KrakenSubscriptionName.valueOf(channelData[0]);

        Integer depth = null;
        if (args.length > 0 && args[0] != null) {
            depth = (Integer) args[0];
        }
        int reqID = Math.abs(UUID.randomUUID().hashCode());
        subscriptionRequestMap.put(reqID, channelName);

        KrakenSubscriptionMessage subscriptionMessage = new KrakenSubscriptionMessage(reqID, subscribe,
                Collections.singletonList(pair), new KrakenSubscriptionConfig(subscriptionName, depth));
        return objectMapper.writeValueAsString(subscriptionMessage);
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        String [] channelData = channelName.split(KrakenStreamingMarketDataService.KRAKEN_CHANNEL_DELIMITER);
        String pair = channelData[1];
        KrakenSubscriptionName subscriptionName = KrakenSubscriptionName.valueOf(channelData[0]);

        int reqID = Math.abs(UUID.randomUUID().hashCode());
        subscriptionRequestMap.put(reqID, channelName);
        KrakenSubscriptionMessage subscriptionMessage = new KrakenSubscriptionMessage(reqID, KrakenEventType.unsubscribe,
                Collections.singletonList(pair), new KrakenSubscriptionConfig(subscriptionName));
        return objectMapper.writeValueAsString(subscriptionMessage);
    }

}
