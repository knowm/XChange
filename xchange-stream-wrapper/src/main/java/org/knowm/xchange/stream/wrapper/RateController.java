package org.knowm.xchange.stream.wrapper;

/**
 * TODO at the moment this is an abstract concept which needs to be combined with the {@link
 * org.knowm.xchange.ExchangeSpecification.ResilienceSpecification} work and fall back to {@link
 * org.knowm.xchange.dto.meta.ExchangeMetaData} if no resilience support is built into the exchange. No concrete
 * implementation yet.
 */
public interface RateController {

  /**
   * Blocks until the operation can succeed. TODO this is currently fairly dumb, but needs combining with the resilience
   * work
   */
  void acquire();

  /**
   * Cuts the throughput rate significantly on a temporary basis. This throttle will expire after a period of time.
   */
  void throttle();

  /**
   * Slightly reduces the throughput permanently. Use on encountering rate limiting errors to reduce the likelihood of
   * hitting it again.
   */
  void backoff();

  /**
   * Blocks any further processing for a period of time. Intended to be used on detection of a rate ban.
   */
  void pause();
}