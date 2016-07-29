package org.knowm.xchange.coinsetter.service.streaming;

import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * Coinsetter configuration for streaming service.
 */
public class CoinsetterStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final Map<String, Object[]> events = new HashMap<String, Object[]>();

  public CoinsetterStreamingConfiguration() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxReconnectAttempts() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getReconnectWaitTimeInMs() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTimeoutInMs() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEncryptedChannel() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean keepAlive() {

    return false;
  }

  public Map<String, Object[]> getEvents() {

    return events;
  }

  /**
   * Add event channel to subscribe. The event could be:
   * <ul>
   * <li>last room</li>
   * <li>ticker room</li>
   * <li>depth room</li>
   * <li>levels room</li>
   * <li>levels_COINSETTER room</li>
   * <li>levels_SMART room</li>
   * <li>orders room</li>
   * </ul>
   *
   * @param event the event channels.
   * @param args event arguments.
   */
  public void addEvent(String event, Object... args) {

    this.events.put(event, args);
  }

  public void addAllMarketDataEvents() {

    for (String event : new String[] { "last room", "ticker room", "depth room", "levels room", }) {
      addEvent(event);
    }
  }

}
