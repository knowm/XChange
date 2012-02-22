package com.xeiam.xchange.streaming.socketio.demo;

import com.xeiam.xchange.streaming.socketio.IOAcknowledge;
import com.xeiam.xchange.streaming.socketio.IOCallback;
import com.xeiam.xchange.streaming.socketio.SocketIO;
import com.xeiam.xchange.streaming.socketio.SocketIOException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * <p>Socket IO client to provide the following to XChange:</p>
 * <ul>
 * <li>Demonstration of connection to exchange server over socket IO</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SocketIOExchangeClient implements IOCallback {
  
  public static void main(String[] args) throws MalformedURLException, InterruptedException {

    // Require a client to respond to events
    SocketIOExchangeClient client = new SocketIOExchangeClient();

    // TODO HTTPS handshake working for MtGox demo
    // new SocketIO("https://socketio.mtgox.com/mtgox",client);
    // TODO HTTPS handshake working for internal exchange demo
    // new SocketIO("http://localhost:8887",client);

    // Connect to MtGox over HTTP
    new SocketIO("http://socketio.mtgox.com/mtgox",client);

  }

  @Override
  public void onDisconnect() {
    System.out.println("Disconnected");
  }

  @Override
  public void onConnect() {
    System.out.println("Connected");
    
  }

  @Override
  public void onMessage(String data, IOAcknowledge ack) {
    System.out.println("Message: "+data);
  }

  @Override
  public void onMessage(JSONObject json, IOAcknowledge ack) {
    System.out.println("JSON message: "+json.toString());
  }

  @Override
  public void on(String event, IOAcknowledge ack, Object... args) {
    System.out.println("Event: "+event);
  }

  @Override
  public void onError(SocketIOException socketIOException) {
    System.out.println("Error: "+socketIOException.getMessage());
  }
}
