package org.knowm.xchange.mexbt.service.streaming;

import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

public class MeXBTExchangeStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final boolean subscribeTicker;
  private final String[] inses;

  /**
   * Constructs a configuration.
   *
   * @param subscribeTicker indicates to subscribe ticker.
   * @param inses indicates what ins should be subscribed in live trades and order book.
   */
  public MeXBTExchangeStreamingConfiguration(boolean subscribeTicker, String[] inses) {
    this.subscribeTicker = subscribeTicker;
    this.inses = inses;
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

  public boolean isSubscribeTicker() {
    return subscribeTicker;
  }

  public String[] getInses() {
    return inses;
  }

}
