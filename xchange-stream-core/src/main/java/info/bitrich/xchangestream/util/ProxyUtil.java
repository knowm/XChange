package info.bitrich.xchangestream.util;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Foat Akhmadeev 18/06/2018 */
public class ProxyUtil {
  private static final Logger LOG = LoggerFactory.getLogger(ProxyUtil.class);

  private Process proxyProcess;
  private ScheduledExecutorService scheduler;

  private String execLine;
  private long waitTimeMillis;

  public ProxyUtil(String execLine, long waitTimeMillis) {
    scheduler = Executors.newScheduledThreadPool(1);
    this.execLine = execLine;
    this.waitTimeMillis = waitTimeMillis;
  }

  public void startProxy() throws Exception {
    ScheduledFuture<Process> schedule =
        scheduler.schedule(
            () -> {
              Process p = null;
              try {
                p = Runtime.getRuntime().exec(execLine);
              } catch (IOException e) {
                LOG.error(e.getMessage(), e);
              }
              return p;
            },
            1,
            TimeUnit.SECONDS);

    Thread.sleep(waitTimeMillis);

    proxyProcess = schedule.get();
  }

  public void stopProxy() {
    proxyProcess.destroy();
  }

  public void shutdown() {
    if (proxyProcess.isAlive()) proxyProcess.destroy();
    scheduler.shutdown();
  }
}
