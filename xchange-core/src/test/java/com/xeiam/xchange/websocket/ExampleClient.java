package com.xeiam.xchange.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Ignore;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
@Ignore
public class ExampleClient extends WebSocketClient {

  public ExampleClient(URI serverUri, Draft draft) {

    super(serverUri, draft);
  }

  public ExampleClient(URI serverURI) {

    super(serverURI);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {

    System.out.println("opened connection");
    // if you pan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
  }

  @Override
  public void onMessage(String message) {

    System.out.println("received: " + message);
    // send( "you said: " + message );
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {

    // The codecodes are documented in class org.java_websocket.framing.CloseFrame
    System.out.println("Connection closed by " + (remote ? "remote peer" : "us"));
    System.out.println("reason= " + reason);
  }

  @Override
  public void onError(Exception ex) {

    ex.printStackTrace();
    // if the error is fatal then onClose will be called additionally
  }

  public static void main(String[] args) throws URISyntaxException {

    // ExampleClient c = new ExampleClient(new URI("ws://websocket.mtgox.com:80/mtgox?Currency=EUR&Channel=dbf1dee9-4f2e-4a08-8cb7-748919a71b21"), new Draft_10()); // more about drafts here:
    ExampleClient c = new ExampleClient(new URI("ws://websocket.mtgox.com:80/mtgox?Currency=EUR"), new Draft_10()); // more about drafts here:
    // http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
    c.connect();
  }

}