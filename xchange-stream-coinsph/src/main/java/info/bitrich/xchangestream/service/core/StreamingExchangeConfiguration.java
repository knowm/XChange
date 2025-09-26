package info.bitrich.xchangestream.service.core;

import java.time.Duration;

/** Configuration for streaming exchange services */
public class StreamingExchangeConfiguration {
  private final Duration connectionTimeout;
  private final Duration retryDuration;
  private final int idleTimeout;

  /** Create a new configuration with default values. */
  public StreamingExchangeConfiguration() {
    this(Duration.ofSeconds(10), Duration.ofSeconds(15), 0);
  }

  /**
   * Create a new configuration with custom connection timeout and retry duration.
   *
   * @param connectionTimeout the websocket connection timeout
   * @param retryDuration the duration to wait between retries
   * @param idleTimeout the idle timeout in seconds (0 means no timeout)
   */
  public StreamingExchangeConfiguration(
      Duration connectionTimeout, Duration retryDuration, int idleTimeout) {
    this.connectionTimeout = connectionTimeout;
    this.retryDuration = retryDuration;
    this.idleTimeout = idleTimeout;
  }

  public Duration getConnectionTimeout() {
    return connectionTimeout;
  }

  public Duration getRetryDuration() {
    return retryDuration;
  }

  public int getIdleTimeout() {
    return idleTimeout;
  }
}
