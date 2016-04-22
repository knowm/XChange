package org.knowm.xchange.coinsetter.service.streaming;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterExchange;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterSocketListener;
import org.knowm.xchange.service.BaseExchangeService;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Coinsetter Websockets API implementation, fires socket events.
 */
public class CoinsetterSocket extends BaseExchangeService {

  private final Logger log = LoggerFactory.getLogger(CoinsetterSocket.class);

  private final CoinsetterStreamingConfiguration coinsetterStreamingConfiguration;

  private final SocketIO socket;

  private final List<CoinsetterSocketListener> socketIOListeners = new ArrayList<CoinsetterSocketListener>();

  public CoinsetterSocket(Exchange exchange, CoinsetterStreamingConfiguration coinsetterStreamingConfiguration) {

    super(exchange);

    this.coinsetterStreamingConfiguration = coinsetterStreamingConfiguration;

    try {
      SocketIO.setDefaultSSLSocketFactory(SSLContext.getDefault());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    final String uri = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(CoinsetterExchange.WEBSOCKET_URI_KEY);

    try {
      socket = new SocketIO(uri);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public void addListener(CoinsetterSocketListener listener) {

    this.socketIOListeners.add(listener);
  }

  public void connect() {

    socket.connect(new IOCallback() {

      @Override
      public void onMessage(JsonElement json, IOAcknowledge ack) {

        log.trace("onMessage: {}, {}", json, ack);
        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.onMessage(json, ack);
        }
      }

      @Override
      public void onMessage(String data, IOAcknowledge ack) {

        log.trace("onMessage: {}, {}", data, ack);
        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.onMessage(data, ack);
        }
      }

      @Override
      public void onError(SocketIOException socketIOException) {

        log.trace("onError: {}", socketIOException);
        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.onError(socketIOException);
        }
      }

      @Override
      public void onDisconnect() {

        log.trace("onDisconnect");
        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.onDisconnect();
        }
      }

      @Override
      public void onConnect() {

        log.trace("onConnect");
        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.onConnect();
        }
      }

      @Override
      public void on(String event, IOAcknowledge ack, JsonElement... args) {

        log.trace("on: {} {} {}", event, ack, args);

        for (CoinsetterSocketListener listener : socketIOListeners) {
          listener.on(event, ack, args);
        }
      }

    });

    // This line is cached until the connection is established.
    for (Map.Entry<String, Object[]> event : coinsetterStreamingConfiguration.getEvents().entrySet()) {
      socket.emit(event.getKey(), event.getValue());
    }
  }

  public void disconnect() {

    socket.disconnect();
  }

}
