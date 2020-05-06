package info.bitrich.xchangestream.poloniex2;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketEvent;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketEventsTransaction;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketOrderbookModifiedEvent;
import info.bitrich.xchangestream.poloniex2.dto.PoloniexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 10.11.17. */
public class PoloniexStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(PoloniexStreamingService.class);

  private static final String HEARTBEAT = "1010";

  private final Map<String, String> subscribedChannels = new HashMap<>();
  private final Map<String, Observable<JsonNode>> subscriptions = new HashMap<>();

  public PoloniexStreamingService(String apiUrl) {
    super(apiUrl, Integer.MAX_VALUE, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_RETRY_DURATION, 2);
  }

  @Override
  protected void handleMessage(JsonNode message) {

    if (message.isArray()) {
      if (message.size() < 3) {
        if (message.get(0).asText().equals(HEARTBEAT)) return;
        else if (message.get(0).asText().equals("1002")) return;
      }
      int channelId = Integer.parseInt(message.get(0).toString());
      if (channelId > 0 && channelId < 1000) {
        JsonNode events = message.get(2);
        if (events != null && events.isArray()) {
          JsonNode event = events.get(0);
          if (event.get(0).toString().equals("\"i\"")) {
            if (event.get(1).has("orderBook")) {
              subscribedChannels.compute(
                  String.valueOf(channelId),
                  (key, oldValue) -> {
                    String currencyPair = event.get(1).get("currencyPair").asText();
                    if (oldValue != null && !oldValue.equals(currencyPair)) {
                      throw new RuntimeException("Attempted currency pair channel id reassignment");
                    }
                    if (oldValue == null) {
                      LOG.info("Register {} as {}", channelId, currencyPair);
                    } else {
                      LOG.debug("Order book reinitialization {} {}", channelId, currencyPair);
                    }
                    return currencyPair;
                  });
            }
          }
        }
      }
    }
    if (message.has("error")) {
      LOG.error("Error with message: " + message.get("error").asText());
      return;
    }
    super.handleMessage(message);
  }

  @Override
  public boolean processArrayMassageSeparately() {
    return false;
  }

  @Override
  public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
    if (!channels.containsKey(channelName)) {
      Observable<JsonNode> subscription = super.subscribeChannel(channelName, args);
      subscriptions.put(channelName, subscription);
    }

    return subscriptions.get(channelName);
  }

  public Observable<List<PoloniexWebSocketEvent>> subscribeCurrencyPairChannel(
      CurrencyPair currencyPair) {
    String channelName = currencyPair.counter.toString() + "_" + currencyPair.base.toString();
    return subscribeChannel(channelName)
        .map(
            jsonNode ->
                objectMapper.treeToValue(jsonNode, PoloniexWebSocketEventsTransaction.class))
        .scan(
            (poloniexWebSocketEventsTransactionOld, poloniexWebSocketEventsTransactionNew) -> {
              final boolean initialSnapshot =
                  poloniexWebSocketEventsTransactionNew.getEvents().stream()
                      .anyMatch(PoloniexWebSocketOrderbookModifiedEvent.class::isInstance);
              final boolean sequenceContinuous =
                  poloniexWebSocketEventsTransactionOld.getSeqId() + 1
                      == poloniexWebSocketEventsTransactionNew.getSeqId();
              if (!initialSnapshot || sequenceContinuous) {
                return poloniexWebSocketEventsTransactionNew;
              } else {
                throw new RuntimeException(
                    String.format(
                        "Invalid sequencing, old: %s new: %s",
                        objectMapper.writeValueAsString(poloniexWebSocketEventsTransactionOld),
                        objectMapper.writeValueAsString(poloniexWebSocketEventsTransactionNew)));
              }
            })
        .map(PoloniexWebSocketEventsTransaction::getEvents)
        .share();
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    String strChannelId = message.get(0).asText();
    int channelId = Integer.parseInt(strChannelId);
    if (channelId >= 1000) return strChannelId;
    else return subscribedChannels.get(message.get(0).asText());
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    PoloniexWebSocketSubscriptionMessage subscribeMessage =
        new PoloniexWebSocketSubscriptionMessage("subscribe", channelName);
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    PoloniexWebSocketSubscriptionMessage subscribeMessage =
        new PoloniexWebSocketSubscriptionMessage("unsubscribe", channelName);
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public Completable disconnect() {

    return super.disconnect();
  }
}
