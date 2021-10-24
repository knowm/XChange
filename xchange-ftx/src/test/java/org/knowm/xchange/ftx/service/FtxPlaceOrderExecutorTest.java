package org.knowm.xchange.ftx.service;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class FtxPlaceOrderExecutorTest {

  private FtxPlaceOrderExecutor executor = new FtxPlaceOrderExecutor();

  @Test
  public void testNoSleep() throws IOException {
    long t0 = System.currentTimeMillis();
    executor.executePlace(() -> emptyPlaceOrder(null));
    executor.executePlace(() -> emptyPlaceOrder(null));
    long t1 = System.currentTimeMillis();

    assertThat(t1 - t0 < 200).isTrue();
  }

  @Test
  public void testSingleSleep() throws IOException {
    long t0 = System.currentTimeMillis();
    executor.executePlace(() -> emptyPlaceOrder(null));
    executor.executePlace(() -> emptyPlaceOrder(null));

    executor.executePlace(() -> emptyPlaceOrder(null));
    executor.executePlace(() -> emptyPlaceOrder(null));
    long t1 = System.currentTimeMillis();

    assertThat(t1 - t0 > 200).isTrue();
    assertThat(t1 - t0 < 400).isTrue();
  }

  @Test
  public void testException() {
    assertThatCode(
            () ->
                executor.executePlace(
                    () -> emptyPlaceOrder(new IOException("Expected IOException"))))
        .hasMessage("Expected IOException");
  }

  @Test
  public void testMany() throws Exception {
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    List<Long> timestamps = Collections.synchronizedList(new ArrayList<>());
    
    for (int i = 0; i < 10; i++) {
      threadPool.submit(
          () -> timestamps.add(Long.valueOf(executor.executePlace(() -> emptyPlaceOrder(null)))));
    }
    
    threadPool.shutdown();
    boolean terminated = threadPool.awaitTermination(6, TimeUnit.SECONDS);
    
    assertThat(terminated).isTrue();
    assertThat(timestamps.size()).isEqualTo(10);

    for (int i = 0; i < 4; i++) {
      long delay = timestamps.get(2 * (i + 1)) - timestamps.get(2 * i);
      System.out.println("Place order delay: " + delay);
      assertThat(delay > 200).isTrue();
    }
  }

  private String emptyPlaceOrder(IOException exception) throws IOException {
    if (exception != null) throw exception;
    return String.valueOf(System.currentTimeMillis());
  }
}
