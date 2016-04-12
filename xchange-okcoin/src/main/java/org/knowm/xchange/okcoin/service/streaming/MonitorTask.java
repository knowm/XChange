package org.knowm.xchange.okcoin.service.streaming;

import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;

class MonitorTask extends TimerTask {
  private long startTime = System.currentTimeMillis();
  private final int checkTime = 5000;
  private WebSocketBase client = null;

  public void updateTime() {
    startTime = System.currentTimeMillis();
  }

  public MonitorTask(WebSocketBase client) {
    this.client = client;
  }

  @Override
  public void run() {
    if (System.currentTimeMillis() - startTime > checkTime) {
      client.setStatus(false);

      client.reConnect();
    }
    try {
      client.sendPing();
    } catch (RejectedExecutionException reject) {
      client.setStatus(false);

      client.reConnect();
    }
  }
}