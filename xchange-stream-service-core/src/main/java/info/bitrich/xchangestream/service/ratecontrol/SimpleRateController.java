package info.bitrich.xchangestream.service.ratecontrol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRateController implements RateController {

  private static final Logger LOG = LoggerFactory.getLogger(SimpleRateController.class);

  private final long rateLimitRetryMs;
  private final String name;
  private final AtomicReference<CountDownLatch> rateLimiter =
      new AtomicReference<>(new CountDownLatch(0));
  private final ScheduledExecutorService executorService =
      Executors.newSingleThreadScheduledExecutor();

  public SimpleRateController(long rateLimitIntervalMs, final String name) {
    this.rateLimitRetryMs = rateLimitIntervalMs;
    this.name = name;
  }

  @Override
  public void acquire() {
    try {
      final CountDownLatch latch = rateLimiter.get();
      if (latch.getCount() > 0) {
        LOG.info("{} rate-controller enabled; waiting until rate-controller is disabled", name);
      }
      latch.await();
    } catch (InterruptedException e) {
      LOG.error("Interrupted exception while awaiting rate-controller: {}", e.getMessage());
    }
  }

  /**
   * Method is used to completely stop requests for rateLimitRetryMs. A CountDownLatch is used to
   * track if the rate-limiter is currently enabled.
   *
   * <p>The CountDownLatch is kept inside of an AtomicReference to ensure queries and modifications
   * happen atomically. If this method is called at the same time by two competing threads only a
   * single instance of the CountDownLatch created and the same instance will be returned. A new
   * CountDownLatch will only be created when the existing CountDownLatch has a count of 0.
   *
   * <p>The countDown() is then scheduled to happen after rate-limiter interval has passed.
   */
  @Override
  public void halt() {
    rateLimiter.updateAndGet(
        currLatch -> {
          if (currLatch.getCount() > 0) {
            return currLatch;
          }
          final CountDownLatch newLatch = new CountDownLatch(1);
          LOG.info("{} rate-controller enabled for {}ms", name, rateLimitRetryMs);

          // schedules rate limiter to be disabled after time has passed
          executorService.schedule(newLatch::countDown, rateLimitRetryMs, TimeUnit.MILLISECONDS);

          return newLatch;
        });
  }

  @Override
  public void throttle() {}

  @Override
  public void backoff() {}
}
