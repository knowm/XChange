package org.knowm.xchange.cryptocom;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Crypto.com requires a strictly increasing request id on every signed REST call and every
 * WebSocket message; shared by {@link CryptoComExchange} and the streaming module's connection
 * classes so both keep the same "seed from current time, then increment" scheme in one place.
 */
public final class CryptoComRequestIdGenerator {

  private final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

  public long next() {
    return counter.incrementAndGet();
  }
}
