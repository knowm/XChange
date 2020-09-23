package info.bitrich.xchangestream.service.ratecontrol;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public class SimpleRateControllerTest {

  @Test
  public void noHaltPassThru() throws ExecutionException, InterruptedException, TimeoutException {

    final SimpleRateController rateController = new SimpleRateController(30000, "testcontroller");

    final AtomicInteger ai = new AtomicInteger(0);

    CompletableFuture<Void> future1 = new CompletableFuture<>();
    new Thread(
            () -> {
              rateController.acquire();
              ai.incrementAndGet();
              future1.complete(null);
            })
        .start();

    future1.get(5, TimeUnit.SECONDS);

    Assert.assertEquals(ai.get(), 1);
  }

  @Test
  public void blockUntilReleased()
      throws ExecutionException, InterruptedException, TimeoutException {

    final SimpleRateController rateController = new SimpleRateController(3000, "testcontroller");
    rateController.halt();

    final AtomicInteger ai = new AtomicInteger(0);

    CompletableFuture<Void> future1 = new CompletableFuture<>();
    new Thread(
            () -> {
              rateController.acquire();
              ai.incrementAndGet();
              future1.complete(null);
            })
        .start();

    future1.get(5, TimeUnit.SECONDS);

    Assert.assertEquals(ai.get(), 1);
  }

  @Test
  public void submitMultipleHalts()
      throws ExecutionException, InterruptedException, TimeoutException {

    final SimpleRateController rateController = new SimpleRateController(3000, "testcontroller");

    new Thread(rateController::halt).start();
    new Thread(rateController::halt).start();
    new Thread(rateController::halt).start();
    new Thread(rateController::halt).start();

    final AtomicInteger ai = new AtomicInteger(0);

    CompletableFuture<Void> future1 = new CompletableFuture<>();
    new Thread(
            () -> {
              ai.incrementAndGet();
              future1.complete(null);
            })
        .start();

    future1.get(5, TimeUnit.SECONDS);

    Assert.assertEquals(ai.get(), 1);
  }

  @Test
  public void blockUntilReleasedMultipleWait()
      throws ExecutionException, InterruptedException, TimeoutException {

    final SimpleRateController rateController = new SimpleRateController(3000, "testcontroller");
    rateController.halt();

    final AtomicInteger ai = new AtomicInteger(0);

    CompletableFuture<Void> future1 = new CompletableFuture<>();
    new Thread(
            () -> {
              rateController.acquire();
              ai.incrementAndGet();
              future1.complete(null);
            })
        .start();

    CompletableFuture<Void> future2 = new CompletableFuture<>();
    new Thread(
            () -> {
              rateController.acquire();
              ai.incrementAndGet();
              future2.complete(null);
            })
        .start();

    CompletableFuture.allOf(new CompletableFuture[] {future1, future2}).get(5, TimeUnit.SECONDS);

    Assert.assertEquals(ai.get(), 2);
  }
}
