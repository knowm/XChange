package info.bitrich.xchangestream.btcmarkets;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.bitrich.xchangestream.btcmarkets.dto.BTCMarketsWebSocketSubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BTCMarketsStreamingService extends JsonNettyStreamingService {
    static final String CHANNEL_ORDERBOOK = "orderbook";
    static final String CHANNEL_TICK = "tick";
    static final String CHANNEL_HEARTBEAT = "heartbeat";
    private static final Logger LOG = LoggerFactory.getLogger(BTCMarketsStreamingService.class);
    private final Set<String> subscribedOrderbooks = Sets.newConcurrentHashSet();
    private final Set<String> subscribedtick = Sets.newConcurrentHashSet();

    public BTCMarketsStreamingService(String apiUrl) {
        super(apiUrl);
    }

    private BTCMarketsWebSocketSubscribeMessage buildSubscribeMessage() {
        return new BTCMarketsWebSocketSubscribeMessage(
                new ArrayList<>(subscribedtick),
                Lists.newArrayList(CHANNEL_ORDERBOOK, CHANNEL_HEARTBEAT, CHANNEL_TICK),
                null,
                null,
                null);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
        final String messageType = message.get("messageType").asText();
        if (messageType.startsWith(CHANNEL_TICK)) {
            return messageType + ":" + message.get("marketId").asText();
        } else if (messageType.startsWith(CHANNEL_ORDERBOOK)) {
            return messageType + ":" + message.get("marketId").asText();
        } else
            return messageType;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        if (CHANNEL_TICK.equals(channelName)) {
            subscribedtick.add(args[0].toString());
            LOG.debug("Now subscribed to Ticker {}", subscribedtick);
            return objectMapper.writeValueAsString(buildSubscribeMessage());
        } else if (CHANNEL_ORDERBOOK.equals(channelName)) {
            subscribedOrderbooks.add(args[0].toString());
            LOG.debug("Now subscribed to Orderbook {}", subscribedOrderbooks);
            return objectMapper.writeValueAsString(buildSubscribeMessage());
        } else {
            throw new IllegalArgumentException(
                    "Can't create subscribe messsage for channel " + channelName);
        }
    }

    public String getSubscriptionUniqueId(String channelName, Object... args) {
        if (CHANNEL_TICK.equals(channelName)) {
            return channelName + ":" + args[0].toString();
        } else if (CHANNEL_ORDERBOOK.equals(channelName)) {
            return channelName + ":" + args[0].toString();
        } else {
            return channelName;
        }
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        if (channelName.startsWith(CHANNEL_TICK)) {
            subscribedtick.remove(channelName);
            return objectMapper.writeValueAsString(buildSubscribeMessage());
        } else if (channelName.startsWith(CHANNEL_ORDERBOOK)) {
            subscribedOrderbooks.remove(channelName);
            return objectMapper.writeValueAsString(buildSubscribeMessage());
        } else {
            return null;
        }
    }
}
