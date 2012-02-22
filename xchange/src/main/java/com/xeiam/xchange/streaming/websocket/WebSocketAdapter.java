package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.FrameData.Opcode;

import java.io.IOException;

public abstract class WebSocketAdapter implements WebSocketListener {

  @Override
  public HandshakeBuilder onHandshakeRecievedAsServer(WebSocket conn, Draft draft, HandshakeData request) throws IOException {
    return new DefaultHandshakeData();
  }

  @Override
  public boolean onHandshakeRecievedAsClient(WebSocket conn, HandshakeData request, HandshakeData response) throws IOException {
    return true;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
  }

  @Override
  public void onOpen(WebSocket conn, HandshakeData handshake) {
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
  }

  @Override
  public void onMessage(WebSocket conn, byte[] blob) {
  }

  @Override
  public void onPing(WebSocket conn, FrameData f) {
    DefaultFrameData resp = new DefaultFrameData(f);
    resp.setOptcode(Opcode.PONG);
    try {
      conn.sendFrame(resp);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onPong(WebSocket conn, FrameData f) {
  }

  /**
   * Gets the XML string that should be returned if a client requests a Flash
   * security policy.
   *
   * The default implementation allows access from all remote domains, but
   * only on the port that this WebSocketServer is listening on.
   *
   * This is specifically implemented for gitime's WebSocket client for Flash:
   * http://github.com/gimite/web-socket-js
   *
   * @return An XML String that comforms to Flash's security policy. You MUST
   *         not include the null char at the end, it is appended automatically.
   */
  @Override
  public String getFlashPolicy(WebSocket conn) {
    return "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"" + conn.getLocalSocketAddress().getPort() + "\" /></cross-domain-policy>\0";
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
  }

}
