package com.xeiam.xchange.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author timmolter
 */
public class WebSocketEventProducer extends WebSocketClient {

  private final Logger logger = LoggerFactory.getLogger(WebSocketEventProducer.class);

  private final BlockingQueue<ExchangeEvent> producerEventQueue;

  /**
   * Constructor
   * 
   * @param url
   * @param exchangeEventProducer
   * @throws URISyntaxException
   */
  public WebSocketEventProducer(String url, BlockingQueue<ExchangeEvent> producerEventQueue) throws URISyntaxException {

    super(new URI(url), new Draft_10());
    this.producerEventQueue = producerEventQueue;

  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {

    // System.out.println("opened connection");
    // if you pan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient

    logger.debug("connected");
    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.CONNECT, "connected");
    addToQueue(exchangeEvent);
  }

  @Override
  public void onMessage(String message) {

    // System.out.println("received: " + message);
    // send( "you said: " + message );

    // logger.debug(message);
    ExchangeEvent exchangeEvent = new DefaultExchangeEvent(ExchangeEventType.MESSAGE, message);
    addToQueue(exchangeEvent);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {

    // The codecodes are documented in class org.java_websocket.framing.CloseFrame
    // System.out.println("Connection closed by " + (remote ? "remote peer" : "us"));
    // System.out.println("reason= " + reason);

    logger.debug("onDisconnect");
    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.DISCONNECT, "disconnected");
    addToQueue(exchangeEvent);
  }

  @Override
  public void onError(Exception ex) {

    // ex.printStackTrace();
    // if the error is fatal then onClose will be called additionally

    logger.error("onError: {}", ex.getMessage(), ex);
    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.ERROR, ex.getMessage());
    addToQueue(exchangeEvent);
  }

  private void addToQueue(ExchangeEvent exchangeEvent) {

    try {
      producerEventQueue.put(exchangeEvent);
    } catch (InterruptedException e) {
      logger.warn("InterruptedException occurred while adding ExchangeEvent to Queue!", e);
    }
  }

}