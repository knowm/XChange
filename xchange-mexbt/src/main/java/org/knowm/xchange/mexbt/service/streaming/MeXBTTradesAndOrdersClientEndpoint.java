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

import org.knowm.xchange.mexbt.MeXBTAdapters;
import org.knowm.xchange.mexbt.dto.streaming.MeXBTStreamingTradeOrOrder;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;

@ClientEndpoint(decoders = MeXBTTradesAndOrdersDecoder.class)
public final class MeXBTTradesAndOrdersClientEndpoint {

  private final Logger log = LoggerFactory.getLogger(MeXBTTradesAndOrdersClientEndpoint.class);
  private final BlockingQueue<ExchangeEvent> exchangeEvents;
  private final String ins;

  public MeXBTTradesAndOrdersClientEndpoint(BlockingQueue<ExchangeEvent> exchangeEvents, String ins) {
    this.exchangeEvents = exchangeEvents;
    this.ins = ins;
  }

  @OnOpen
  public void onOpen(Session session, EndpointConfig config) {
    String msg = String.format("{\"ins\": \"%s\"}", ins);
    log.trace("onOpen: {}", msg);
    session.getAsyncRemote().sendText(msg);
  }

  @OnMessage
  public void onMessage(Session session, MeXBTStreamingTradeOrOrder[] toos) throws InterruptedException {
    for (MeXBTStreamingTradeOrOrder too : toos) {
      switch (too.getAction()) {
      case 0:
        exchangeEvents.put(new DefaultExchangeEvent(ExchangeEventType.ORDER_ADDED, null, MeXBTAdapters.adaptOrder(ins, too)));
        break;
      case 1:
        exchangeEvents.put(new DefaultExchangeEvent(ExchangeEventType.TRADE, null, MeXBTAdapters.adaptTrade(ins, too)));
        break;
      default:
        break;
      }
    }
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
