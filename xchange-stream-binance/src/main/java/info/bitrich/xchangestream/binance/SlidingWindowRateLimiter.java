package info.bitrich.xchangestream.binance;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;

import static java.lang.System.nanoTime;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.locks.LockSupport.parkNanos;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final long nanoTimeStart;
    private final String name;
    private final RateLimiterConfig config;
    private final long[] deadlines;
    private int current;

    public SlidingWindowRateLimiter(String name, RateLimiterConfig config) {
        this.nanoTimeStart = nanoTime();
        this.name = name;
        this.config = config;
        deadlines = new long[config.getLimitForPeriod()];
        current = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void changeTimeoutDuration(Duration timeoutDuration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeLimitForPeriod(int limitForPeriod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean acquirePermission(int permits) {
        for (int i = 0; i < permits; i++) {
            long waitNanos = deadlines[current] - currentNanoTime();
            if (!waitForPermission(waitNanos))
                return false;
            deadlines[current++] = currentNanoTime() + config.getLimitRefreshPeriod().toNanos();
            if (current == deadlines.length)
                current = 0;
        }
        return true;
    }

    @Override
    public long reservePermission(int permits) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drainPermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RateLimiterConfig getRateLimiterConfig() {
        return config;
    }

    @Override
    public io.vavr.collection.Map<String, String> getTags() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Metrics getMetrics() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventPublisher getEventPublisher() {
        throw new UnsupportedOperationException();
    }

    private boolean waitForPermission(final long nanosToWait) {
        long deadline = currentNanoTime() + nanosToWait;
        boolean wasInterrupted = false;
        while (currentNanoTime() < deadline && !wasInterrupted) {
            long sleepBlockDuration = deadline - currentNanoTime();
            parkNanos(sleepBlockDuration);
            wasInterrupted = Thread.interrupted();
        }
        if (wasInterrupted) {
            currentThread().interrupt();
        }
        return !wasInterrupted;
    }

    private long currentNanoTime() {
        return nanoTime() - nanoTimeStart;
    }
}
