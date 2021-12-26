package info.bitrich.xchangestream.btcmarkets;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.btcmarkets.dto.BTCMarketsWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BTCMarketsStreamingService extends JsonNettyStreamingService {
  //	TODO change to enumerator
  static final String CHANNEL_ORDERBOOK = "orderbook";
  static final String CHANNEL_HEARTBEAT = "heartbeat";
  static final String CHANNEL_TICKER = "tick";
  static final String CHANNEL_TRADE = "trade";

  private static final Logger LOG = LoggerFactory.getLogger(BTCMarketsStreamingService.class);
  private final ConcurrentHashMap<String, Set<String>> subscribedMarketIds =
      new ConcurrentHashMap<String, Set<String>>();

  public BTCMarketsStreamingService(String apiUrl) {
    super(apiUrl);
  }

  /*
   * Implementation renamed from BTCMarketsWebSocketSubscribeMessage to BTCMarketsWebSocketSubscriptionMessage to look after
   * more than just the OrderBook subscription. This new implementation also incorporates the use of adding subscriptions to an existing one
   * instead of having to resubscribing with all channels and all {@code marketIds} every time the data services calls the subscribe methods.
   */
  private BTCMarketsWebSocketSubscriptionMessage buildSubscribeMessage(
      String channelName, Set<String> marketIds) {

    // Create the first subscription message
    if (!hasActiveSubscriptions()) {
      return BTCMarketsWebSocketSubscriptionMessage.getFirstSubscriptionMessage(
          new ArrayList<String>(marketIds),
          Arrays.asList(channelName, CHANNEL_HEARTBEAT),
          null,
          null,
          null);
    } else {
      return BTCMarketsWebSocketSubscriptionMessage.getAddSubscriptionMessage(
          new ArrayList<String>(marketIds), Arrays.asList(channelName), null, null, null);
    }
  }

  private BTCMarketsWebSocketSubscriptionMessage buildRemoveSubscriptionMessage(
      String channelName, Set<String> marketIds) {

    return BTCMarketsWebSocketSubscriptionMessage.getRemoveSubcriptionMessage(
        marketIds == null ? new ArrayList<String>() : new ArrayList<String>(marketIds),
        Arrays.asList(channelName),
        null,
        null,
        null);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    final String messageType = message.get("messageType").asText();
    if (messageType.startsWith(CHANNEL_ORDERBOOK)
        || messageType.startsWith(CHANNEL_TICKER)
        || messageType.startsWith(CHANNEL_TRADE)) {
      return messageType + ":" + message.get("marketId").asText();
    }
    return messageType;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {

    if (CHANNEL_ORDERBOOK.equals(channelName)
        || CHANNEL_TICKER.equals(channelName)
        || CHANNEL_TRADE.equals(channelName)) {

      LOG.debug("Now subscribing to {}:{}", channelName, args);
      Set<String> newMarketIds = new HashSet<String>();
      if (args != null) {
        for (Object marketId : args) {
          newMarketIds.add(marketId.toString());
        }
        // Add the marketIds to the Channel
        Set<String> updateMarketIds = subscribedMarketIds.get(channelName);
        if (updateMarketIds != null) {
          updateMarketIds.addAll(newMarketIds);
          subscribedMarketIds.put(channelName, updateMarketIds);
        } else {
          subscribedMarketIds.put(channelName, newMarketIds);
        }
      }
      LOG.debug(
          "getSubscribeMessage: what is in subscribedMarketIds {} - {} / new marketIds {}",
          channelName,
          subscribedMarketIds.get(channelName),
          newMarketIds);
      return objectMapper.writeValueAsString(buildSubscribeMessage(channelName, newMarketIds));
    } else {
      throw new IllegalArgumentException(
          "Can't create subscribe messsage for channel " + channelName);
    }
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    LOG.debug("Returning unique id {}", channelName + ":" + args[0].toString());
    return channelName + ":" + args[0].toString();
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    if (channelName.startsWith(CHANNEL_ORDERBOOK)
        || channelName.startsWith(CHANNEL_TICKER)
        || channelName.startsWith(CHANNEL_TRADE)) {
      LOG.debug(
          "getUnsubscribeMessage: what is in subscribedMarketIds {}:{}",
          channelName,
          subscribedMarketIds.get(channelName));
      return objectMapper.writeValueAsString(
          buildRemoveSubscriptionMessage(channelName, subscribedMarketIds.remove(channelName)));
    } else {
      return null;
    }
  }

  private Boolean hasActiveSubscriptions() {
    return !channels.isEmpty();
  }
}
