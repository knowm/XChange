package org.knowm.xchange.huobi.service.streaming;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import com.google.gson.JsonElement;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Huobi Socket.
 * <p>
 * Socket IO Wrapper.
 * </p>
 */
public class HuobiSocket {

  private final URL url;
  private final SocketIO socket;
  private final List<HuobiSocketListener> listeners = new ArrayList();

  public HuobiSocket(URL url) {
    this.url = url;

    try {
      SocketIO.setDefaultSSLSocketFactory(SSLContext.getDefault());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    socket = new SocketIO();
  }

  public void connect() throws Exception {
    socket.connect(url, new IOCallback() {

      @Override
      public void onMessage(JsonElement json, IOAcknowledge ack) {
        for (HuobiSocketListener listener : listeners) {
          listener.onMessage(json, ack);
        }
      }

      @Override
      public void onMessage(String data, IOAcknowledge ack) {
        for (HuobiSocketListener listener : listeners) {
          listener.onMessage(data, ack);
        }
      }

      @Override
      public void onError(SocketIOException socketIOException) {
        for (HuobiSocketListener listener : listeners) {
          listener.onError(socketIOException);
        }
      }

      @Override
      public void onDisconnect() {
        for (HuobiSocketListener listener : listeners) {
          listener.onDisconnect();
        }
      }

      @Override
      public void onConnect() {
        for (HuobiSocketListener listener : listeners) {
          listener.onConnect();
        }
      }

      @Override
      public void on(String event, IOAcknowledge ack, JsonElement... args) {
        for (HuobiSocketListener listener : listeners) {
          listener.on(event, ack, args);
        }
      }

    });
  }

  public void disconnect() {
    socket.disconnect();
  }

  public void addListener(HuobiSocketListener listener) {
    listeners.add(listener);
  }

  public void emit(final String event, final Object... args) {
    socket.emit(event, args);
  }

  public void emit(final String event, IOAcknowledge ack, final Object... args) {
    socket.emit(event, ack, args);
  }

}
