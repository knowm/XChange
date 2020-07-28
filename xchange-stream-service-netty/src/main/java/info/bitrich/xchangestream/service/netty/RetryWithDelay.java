package info.bitrich.xchangestream.service.netty;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public class RetryWithDelay implements Function<Flowable<? extends Throwable>, Publisher<?>> {
  private final long retryDelayMillis;

  public RetryWithDelay(final long retryDelayMillis) {
    this.retryDelayMillis = retryDelayMillis;
  }

  @Override
  public Publisher<?> apply(Flowable<? extends Throwable> flowable) throws Exception {
    return flowable.flatMap(
        new Function<Throwable, Publisher<?>>() {
          @Override
          public Publisher<?> apply(Throwable throwable) throws Exception {
            return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
          }
        });
  }
}
