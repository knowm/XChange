package org.knowm.xchange.stream.wrapper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.mockito.Mock;

@Slf4j
public class LoopTest {

  @Mock private Exchange exchange;

  private final CountDownLatch blocked = new CountDownLatch(1);
  private final CountDownLatch shutdown = new CountDownLatch(1);

  private final Set<Thread> threads = Sets.newConcurrentHashSet();

  private RxServiceWrapper<Loop> loop;

  @Before
  public void setup() {
    initMocks(this);
    loop = new RxServiceWrapper<>(new Loop("test", exchange, mock(RateController.class), 100, true));
    loop.getDelegate().setLifecycleListener(new LoopListener() {
      @Override
      public void onBlocked() {
        threads.add(Thread.currentThread());
        blocked.countDown();
      }

      @Override
      public void onStop() {
        shutdown.countDown();
      }
    });
  }

  @Test
  public void testImmediateStartupShutdown() throws InterruptedException {
    assertTrue(loop.start().blockingAwait(2, SECONDS));
    assertTrue(blocked.await(2, SECONDS));
    assertFalse(shutdown.await(2, SECONDS));
    assertTrue(loop.stop().blockingAwait(2, SECONDS));
    assertTrue(shutdown.await(2, SECONDS));
  }

  @Test
  public void testInterrupt() throws InterruptedException {
    assertTrue(loop.start().blockingAwait(2, SECONDS));
    assertTrue(blocked.await(2, SECONDS));
    threads.forEach(Thread::interrupt);
    assertTrue(shutdown.await(2, SECONDS));
  }
}
