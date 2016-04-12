package org.knowm.xchange.service.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.Charsetfunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author timmolter
 * @author ObsessiveOrange
 */
public class WebSocketEventProducer extends WebSocketClient {

  private final Logger logger = LoggerFactory.getLogger(WebSocketEventProducer.class);
  private Framedata splitFrame = new FramedataImpl1();

  private final ExchangeEventListener exchangeEventListener;
  private final ReconnectService reconnectService;

  /**
   * Constructor
   * 
   * @param url
   * @param exchangeEventListener
   * @param headers
   * @throws URISyntaxException
   */
  public WebSocketEventProducer(String url, ExchangeEventListener exchangeEventListener, Map<String, String> headers,
      ReconnectService reconnectService) throws URISyntaxException {

    super(new URI(url), new Draft_17(), headers, 0);
    this.exchangeEventListener = exchangeEventListener;
    this.reconnectService = reconnectService;

  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {

    logger.debug("opened connection");
    // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient

    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.CONNECT, "connected");

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      try {
        reconnectService.intercept(exchangeEvent);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    exchangeEventListener.handleEvent(exchangeEvent);

    synchronized (this) {
      this.notifyAll();
    }
  }

  @Override
  public void onMessage(String message) {

    logger.debug(message);
    ExchangeEvent exchangeEvent = new DefaultExchangeEvent(ExchangeEventType.MESSAGE, message);

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      try {
        reconnectService.intercept(exchangeEvent);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    exchangeEventListener.handleEvent(exchangeEvent);
  }

  @Override
  public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {

    try {
      splitFrame.append(frame);
    } catch (InvalidFrameException e) {
      throw new ExchangeException("Invalid frame recieved");
    }

    if (frame.isFin() == true) {
      try {
        onMessage(Charsetfunctions.stringUtf8(getLastFragmentedMessage().getPayloadData()));
      } catch (InvalidDataException e) {
        throw new ExchangeException("Invalid data recieved");
      }
      splitFrame = new FramedataImpl1();
    }
  }

  public Framedata getLastFragmentedMessage() {

    Framedata returnFrame = splitFrame;
    splitFrame = new FramedataImpl1();
    return returnFrame;
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {

    // The codecodes are documented in class org.java_websocket.framing.CloseFrame
    logger.debug("Connection closed by " + (remote ? "remote peer" : "local client"));
    logger.debug("reason= " + reason);

    logger.debug("onClose");
    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.DISCONNECT, "disconnected");

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      try {
        reconnectService.intercept(exchangeEvent);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    exchangeEventListener.handleEvent(exchangeEvent);
  }

  @Override
  public void onError(Exception ex) {

    ex.printStackTrace();
    // if the error is fatal then onClose will be called additionally

    logger.error("onError: {}", ex.getMessage());
    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.ERROR, ex.getMessage());

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      try {
        reconnectService.intercept(exchangeEvent);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    exchangeEventListener.handleEvent(exchangeEvent);
  }
}