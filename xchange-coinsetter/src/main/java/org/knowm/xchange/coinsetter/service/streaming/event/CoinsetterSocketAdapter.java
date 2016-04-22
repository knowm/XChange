package org.knowm.xchange.coinsetter.service.streaming.event;

import com.google.gson.JsonElement;

import io.socket.IOAcknowledge;
import io.socket.SocketIOException;

/**
 * An abstract adapter class for receiving Coinsetter socket events. The methods in this class are empty. This class exists as convenience for
 * creating listener objects.
 */
public abstract class CoinsetterSocketAdapter implements CoinsetterSocketListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConnect() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDisconnect() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onError(SocketIOException socketIOException) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMessage(String data, IOAcknowledge ack) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMessage(JsonElement json, IOAcknowledge ack) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void on(String event, IOAcknowledge ack, JsonElement... args) {

  }

}
