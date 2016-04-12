package org.knowm.xchange.service.streaming;

import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.WebSocket.READYSTATE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alexnugent
 */
public class ReconnectService {

  private final Logger log = LoggerFactory.getLogger(ReconnectService.class);

  private final ExchangeStreamingConfiguration exchangeStreamingConfiguration;

  private final StreamingExchangeService streamingExchangeService;

  private int numConnectionAttempts = 0;

  Timer timer = new Timer();
  TimerTask reconnectTask = new ReconnectTask();

  /**
   * Constructor
   * 
   * @param streamingExchangeService
   */
  public ReconnectService(StreamingExchangeService streamingExchangeService, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

    this.streamingExchangeService = streamingExchangeService;
    this.exchangeStreamingConfiguration = exchangeStreamingConfiguration;
  }

  public void intercept(ExchangeEvent exchangeEvent) throws Exception {

    reconnectTask.cancel();
    reconnectTask = new ReconnectTask();
    timer.schedule(reconnectTask, exchangeStreamingConfiguration.getTimeoutInMs());

    if (exchangeEvent.getEventType() == ExchangeEventType.ERROR || exchangeEvent.getEventType() == ExchangeEventType.DISCONNECT) {
      try {
        Thread.sleep(exchangeStreamingConfiguration.getReconnectWaitTimeInMs());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      reconnect();
    } else if (exchangeEvent.getEventType() == ExchangeEventType.CONNECT) {
      numConnectionAttempts = 0;
    }

  }

  private void reconnect() throws Exception {

    if (!streamingExchangeService.getWebSocketStatus().equals(READYSTATE.OPEN)) {
      log.debug(
          "ExchangeType Error. Attempting reconnect " + numConnectionAttempts + " of " + exchangeStreamingConfiguration.getMaxReconnectAttempts());

      if (numConnectionAttempts >= exchangeStreamingConfiguration.getMaxReconnectAttempts()) {
        log.debug("Terminating reconnection attempts.");
        streamingExchangeService.disconnect();
        Thread.currentThread().interrupt();
        return;
      }
      streamingExchangeService.disconnect();
      streamingExchangeService.connect();
      numConnectionAttempts++;
    }
  }

  class ReconnectTask extends TimerTask {

    @Override
    public void run() {

      // log.debug("ReconnectTask called; result: " + (streamingExchangeService.getWebSocketStatus().equals(READYSTATE.OPEN) ? "Connection still alive" : "Connection dead - reconnecting"));
      if (!streamingExchangeService.getWebSocketStatus().equals(READYSTATE.OPEN)) {
        log.debug("Time out!");
        timer.purge();
        try {
          reconnect();
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      timer.purge();
      timer.schedule(new ReconnectTask(), exchangeStreamingConfiguration.getTimeoutInMs());
    }
  }

}
