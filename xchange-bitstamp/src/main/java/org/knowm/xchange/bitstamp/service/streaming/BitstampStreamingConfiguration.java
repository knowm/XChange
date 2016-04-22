package org.knowm.xchange.bitstamp.service.streaming;

import java.util.HashSet;
import java.util.Set;

import com.pusher.client.PusherOptions;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * <p>
 * Value object to provide the following
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to Bitstamp exchange streaming API</li>
 * </ul>
 */
public class BitstampStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final int timeoutInMs;
  private final boolean isEncryptedChannel;
  private final String pusherKey;
  private final Set<String> channels;
  private PusherOptions pusherOpts;

  /**
   * Constructor
   *
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param timeoutInMs
   * @param isEncryptedChannel - should it use an encrypted channel or not? (ws vs. wss protocol)
   * @param channel - the specific data channel you want to tap into (https://mtgox.com/api/2/stream/list_public), null if none
   */
  public BitstampStreamingConfiguration(PusherOptions pusherOptions, int maxReconnectAttempts, int reconnectWaitTimeInMs, int timeoutInMs,
      boolean isEncryptedChannel, String pusherKey, Set<String> channels) {

    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.timeoutInMs = timeoutInMs;
    this.isEncryptedChannel = isEncryptedChannel;
    this.pusherKey = pusherKey;
    this.channels = channels;
    pusherOpts = pusherOptions;
  }

  public BitstampStreamingConfiguration() {

    maxReconnectAttempts = 30; // 67 min
    reconnectWaitTimeInMs = 135000; // 2:15
    timeoutInMs = 120000; // 2:00
    isEncryptedChannel = false; // data stream is public
    pusherKey = "de504dc5763aeef9ff52"; // https://www.bitstamp.net/websocket/
    channels = new HashSet<String>();
    channels.add("live_trades");
    channels.add("order_book");
    channels.add("diff_order_book");
    pusherOpts = new PusherOptions();
    pusherOpts.setEncrypted(isEncryptedChannel);
    pusherOpts.setActivityTimeout(4 * timeoutInMs); // Keep-alive interval
    pusherOpts.setPongTimeout(timeoutInMs); // Response timeout
  }

  public final PusherOptions pusherOptions() {

    return pusherOpts;
  }

  @Override
  public int getMaxReconnectAttempts() {

    return maxReconnectAttempts;
  }

  @Override
  public int getReconnectWaitTimeInMs() {

    return reconnectWaitTimeInMs;
  }

  @Override
  public int getTimeoutInMs() {

    return timeoutInMs;
  }

  @Override
  public boolean isEncryptedChannel() {

    return isEncryptedChannel;
  }

  @Override
  public boolean keepAlive() {

    return true; // pusher client always keeps alive
  }

  public String getPusherKey() {

    return pusherKey;
  }

  public Set<String> getChannels() {

    return channels;
  }

}
