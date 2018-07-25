package org.knowm.xchange.bitmex;

/** @author Nikita Belenkiy on 03/07/2018. */
public interface RateLimitUpdateListener {
  void rateLimitUpdate(Integer rateLimit, Integer rateLimitRemaining, Integer rateLimitReset);
}
