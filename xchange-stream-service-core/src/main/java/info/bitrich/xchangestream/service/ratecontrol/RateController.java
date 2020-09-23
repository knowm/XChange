package info.bitrich.xchangestream.service.ratecontrol;

/**
 * Interface for a rate-controller. Can be overridden for an exchange which does not need
 * rate-limiting or has unique rate-limit implementation.
 */
public interface RateController {

  /** Method will block until rate-limiter is disabled */
  void acquire();

  /**
   * Cuts the throughput rate on a temporary basis. This throttle will expire after a period of
   * time.
   */
  void throttle();

  /**
   * Slightly reduces the throughput permanently. Use on encountering rate limiting errors to reduce
   * the likelihood of hitting it again.
   */
  void backoff();

  /**
   * Blocks any further requests for a period of time. Intended to be used on detection of a rate
   * ban.
   */
  void halt();
}
