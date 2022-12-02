package info.bitrich.xchangestream.serum;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerumSubscriptionManager {

  private static final Logger LOG = LoggerFactory.getLogger(SerumSubscriptionManager.class);

  /**
   * Map tracks in-flight requests for subscriptions
   *
   * <p>reqID -> channelName 246 -> accountSubscribe_BTC-USD
   */
  private final Map<Integer, String> inflightSubscriptionMap = new ConcurrentHashMap<>();

  /**
   * Map tracks request ids of a subscription to the internal channelName that is used to refer to
   * that channel
   *
   * <p>reqID -> subID 246 -> 11024
   */
  private final Map<Integer, String> requestIdToChannelName = new ConcurrentHashMap<>();

  /**
   * Map tracks request ids of a subscription to the subscription id that Solana responds with
   *
   * <p>reqID -> subID 246 -> 11024
   */
  private final Map<Integer, Integer> requestIdToSubscriptionId = new ConcurrentHashMap<>();

  /**
   * Map tracks inverse of `subscriptionIdToRequestId
   *
   * <p>subID -> reqID 11024 -> 246
   */
  private final Map<Integer, Integer> subscriptionIdToRequestId = new ConcurrentHashMap<>();

  /**
   * Modifies maps to add a new subscription confirmed by Solana
   *
   * @param reqID used in subscription
   * @param subID assigned by Solana to track the channel
   */
  public void newSubscription(int reqID, int subID) {
    final String channelName = inflightSubscriptionMap.remove(reqID);

    requestIdToSubscriptionId.put(reqID, subID);
    requestIdToChannelName.put(reqID, channelName);
    subscriptionIdToRequestId.put(subID, reqID);

    LOG.info("Channel={} has been subscribed on subscription={}", channelName, subID);
  }

  /**
   * Modifies maps to remove subscriptions we have unsubscribed from
   *
   * @param reqID used in subscription
   * @param status of unsubscription
   */
  public void removedSubscription(int reqID, boolean status) {
    final int subID1 = requestIdToSubscriptionId.remove(reqID);
    final String channelName1 = requestIdToChannelName.remove(reqID);
    subscriptionIdToRequestId.remove(subID1);

    LOG.info(
        "Channel={} has been unsubscribed for subscription={} status={}",
        channelName1,
        subID1,
        status);
  }

  public String getChannelName(int subID) {
    final int reqId = subscriptionIdToRequestId.get(subID);
    return requestIdToChannelName.get(reqId);
  }

  public int generateNewInflightRequest(final String channelName) {
    int reqID = Math.abs(UUID.randomUUID().hashCode());
    inflightSubscriptionMap.put(reqID, channelName);
    return reqID;
  }
}
