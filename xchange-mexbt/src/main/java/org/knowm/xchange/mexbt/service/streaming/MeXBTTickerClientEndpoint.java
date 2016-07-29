package org.knowm.xchange.mexbt.service.streaming;

import java.util.concurrent.BlockingQueue;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.mexbt.dto.streaming.MeXBTStreamingTicker;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;

@ClientEndpoint(decoders = MeXBTTickerDecoder.class)
public final class MeXBTTickerClientEndpoint {

  private final Logger log = LoggerFactory.getLogger(MeXBTTickerClientEndpoint.class);
  private final BlockingQueue<ExchangeEvent> exchangeEvents;

  public MeXBTTickerClientEndpoint(BlockingQueue<ExchangeEvent> exchangeEvents) {
    this.exchangeEvents = exchangeEvents;
  }

  @OnOpen
  public void onOpen(Session session, EndpointConfig config) {
    String msg = "{\"messageType\":\"logon\"}";
    log.trace("onOpen: {}", msg);
    session.getAsyncRemote().sendText(msg);
  }

  @OnMessage
  public void onMessage(Session session, MeXBTStreamingTicker ticker) throws InterruptedException {
    log.trace("onMessage: {}", ticker);
    exchangeEvents.put(new DefaultExchangeEvent(ExchangeEventType.TICKER, null, ticker));
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) {
    log.trace("close: {}, reason: {}", session, reason);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    log.trace("error: {}", session, throwable);
  }

}
