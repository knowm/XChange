package org.knowm.xchange.coinsph;

import java.io.IOException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphTimestampFactory implements SynchronizedValueFactory<Long> {

  private static final Logger LOG = LoggerFactory.getLogger(CoinsphTimestampFactory.class);

  private final Coinsph coinsph;
  private final ExchangeSpecification exchangeSpecification;
  private final ResilienceRegistries resilienceRegistries;

  private Long deltaServerTime; // difference between server time and client time in milliseconds

  private CoinsphTimestampFactory(
      Coinsph coinsph,
      ExchangeSpecification exchangeSpecification,
      ResilienceRegistries resilienceRegistries) {
    this.coinsph = coinsph;
    this.exchangeSpecification = exchangeSpecification;
    this.resilienceRegistries = resilienceRegistries;
  }

  public static CoinsphTimestampFactory createFactory(
      Coinsph coinsph,
      ExchangeSpecification exchangeSpecification,
      ResilienceRegistries resilienceRegistries) {
    CoinsphTimestampFactory factory =
        new CoinsphTimestampFactory(coinsph, exchangeSpecification, resilienceRegistries);
    if (resilienceRegistries
        != null) { // Assuming if resilienceRegistries is provided, client should be resilient
      // Only try to sync time if resilient client is enabled
      factory.resync();
    }
    return factory;
  }

  @Override
  public Long createValue() {
    if (resilienceRegistries != null
        && deltaServerTime
            != null) { // Assuming if resilienceRegistries is provided, client should be resilient
      return System.currentTimeMillis() + deltaServerTime;
    }
    return System.currentTimeMillis();
  }

  public void resync() {
    if (resilienceRegistries
        == null) { // Assuming if resilienceRegistries is NOT provided, client is NOT resilient
      // Do not attempt to sync if resilience is disabled
      deltaServerTime = null; // Ensure it's reset if it was previously set
      return;
    }
    try {
      // Using a simplified resilience mechanism for this internal call
      // Or, if a specific retry/rateLimiter is defined for 'time' endpoint, use that.
      long serverTime = coinsph.time().getServerTime();
      long systemTime = System.currentTimeMillis();
      deltaServerTime = serverTime - systemTime;
      LOG.info("deltaServerTime: {} ms", deltaServerTime);
    } catch (IOException e) {
      LOG.warn("An error occurred while calling Coins.ph time server: {}", e.getMessage());
      LOG.warn(
          "Using System.currentTimeMillis() as fallback for this request, but deltaServerTime will not be updated.");
      // Keep the old deltaServerTime if it exists, otherwise it remains null
    } catch (Exception e) {
      LOG.warn("An unexpected error occurred during time synchronization: {}", e.getMessage(), e);
      // Keep the old deltaServerTime
    }
  }
}
