package info.bitrich.xchangestream.serum;

import static info.bitrich.xchangestream.serum.SerumEventType.subscribe;
import static info.bitrich.xchangestream.serum.SerumEventType.unknown;
import static info.bitrich.xchangestream.serum.SerumEventType.unsubscribe;

import com.fasterxml.jackson.databind.JsonNode;
import com.knowm.xchange.serum.SerumAdapters;
import com.knowm.xchange.serum.SerumConfigs.Commitment;
import com.knowm.xchange.serum.SerumConfigs.SubscriptionType;
import info.bitrich.xchangestream.serum.dto.SerumWsSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import java.io.IOException;
import java.time.Duration;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerumStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(SerumStreamingService.class);

  private final SerumSubscriptionManager subscriptionManager = new SerumSubscriptionManager();

  private final String RESULT = "result";
  private final String SUBSCRIPTION = "subscription";
  private final String PARAMS = "params";
  private final String ID = "id";

  public SerumStreamingService(String apiUrl) {
    super(apiUrl);
  }

  public SerumStreamingService(
      String apiUrl,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds) {
    super(apiUrl, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
  }

  /**
   * Channel name is mapped from the subscription id
   *
   * @param message to extract it from
   */
  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    if (message.has(PARAMS) && message.get(PARAMS).has(SUBSCRIPTION)) {
      final int subID = message.get(PARAMS).get(SUBSCRIPTION).intValue();
      return subscriptionManager.getChannelName(subID);
    }
    return null;
  }

  /**
   * Infers message event type based on if a "result" field is present and if it's a boolean or an
   * int.
   *
   * <p>Boolean indicates that it was an unsubscribe as it determines if it was successfully
   * unsubscribed.
   *
   * <p>Int tells us it's a subscribe as it's the subscription id being used for that channel.
   *
   * <p>https://docs.solana.com/developing/clients/jsonrpc-api#subscription-websocket
   *
   * @param message to infer from
   * @return event type
   */
  private SerumEventType getMessageEvent(final JsonNode message) {
    if (message.has(RESULT)) {
      if (message.get(RESULT).isBoolean()) {
        return unsubscribe;
      }
      if (message.get(RESULT).isInt()) {
        return subscribe;
      }
    }
    return unknown;
  }

  @Override
  protected void handleMessage(final JsonNode message) {
    try {
      if (!message.has(ID)) {
        super.handleMessage(message);
        return;
      }
      final int reqID = message.get(ID).intValue();
      switch (getMessageEvent(message)) {
        case subscribe:
          final int subID = message.get(RESULT).intValue();
          subscriptionManager.newSubscription(reqID, subID);
          break;
        case unsubscribe:
          boolean status = message.get(RESULT).asBoolean();
          subscriptionManager.removedSubscription(reqID, status);
          break;
        case unknown:
        default:
          LOG.error("Unknown message type on msg {}", message);
      }
    } catch (Exception e) {
      LOG.error("Issue processing message {}", message, e);
    }
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length < 3) {
      throw new IllegalArgumentException("Not enough args");
    }
    if (!(args[0] instanceof CurrencyPair)) {
      throw new IllegalArgumentException("arg[0] must be the currency pairs");
    }
    if (!(args[1] instanceof SubscriptionType)) {
      throw new IllegalArgumentException("arg[1] must be the subscription type");
    }
    if (!(args[2] instanceof String)) {
      throw new IllegalArgumentException("arg[1] must be the market data type");
    }
    final SubscriptionType subscriptionType = (SubscriptionType) args[1];
    final String account =
        SerumAdapters.getSolanaDataTypeAddress((CurrencyPair) args[0], (String) args[2]);
    final Commitment commitment =
        args.length > 3 && args[2] != null && args[2] instanceof Commitment
            ? (Commitment) args[2]
            : Commitment.max;

    int reqID = subscriptionManager.generateNewInflightRequest(channelName);
    return new SerumWsSubscriptionMessage(commitment, subscriptionType, account, reqID).buildMsg();
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }

  public String buildChannelName(
      final CurrencyPair pair, final SubscriptionType subscriptionType, final Object... args) {
    switch (subscriptionType) {
      case accountSubscribe:
        if (args == null || args.length < 1 || !(args[0] instanceof String)) {
          throw new IllegalArgumentException(
              String.format("No/incorrect market data type for %s specified", pair.toString()));
        }
        final String marketDataType = (String) args[0];
        return subscriptionType.name()
            + "_"
            + SerumAdapters.getSolanaDataTypeAddress(pair, marketDataType);
    }
    throw new UnsupportedOperationException(
        String.format("Unsupported subscription type %s", subscriptionType));
  }
}
