package com.xeiam.xchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alexnugent
 * @create Mar 20, 2013
 */
public class ReconnectService {

  private int maxConnectionAttempts = 10;
  private final StreamingExchangeService streamingExchangeService;
  private int numConnectionAttempts = 0;
  private final Logger log = LoggerFactory.getLogger(ReconnectService.class);

  public ReconnectService(StreamingExchangeService streamingExchangeService) {

    this.streamingExchangeService = streamingExchangeService;
  }

  public void intercept(ExchangeEvent exchangeEvent) {

    if (exchangeEvent.getEventType() == ExchangeEventType.ERROR) {
      reconnect();
    } else if (exchangeEvent.getEventType() == ExchangeEventType.CONNECT) {
      numConnectionAttempts = 0;
    }

  }

  private void reconnect() {

    log.debug("ExchangeType Error. Attempting reconnect " + numConnectionAttempts + " of " + maxConnectionAttempts);

    if (numConnectionAttempts > maxConnectionAttempts) {
      log.debug("Terminating reconnection attempts.");
      return;
    }

    streamingExchangeService.connect();
    numConnectionAttempts++;

  }

  public int getMaxConnectionAttempts() {

    return maxConnectionAttempts;
  }

  public void setMaxConnectionAttempts(int maxConnectionAttempts) {

    this.maxConnectionAttempts = maxConnectionAttempts;
  }

}
