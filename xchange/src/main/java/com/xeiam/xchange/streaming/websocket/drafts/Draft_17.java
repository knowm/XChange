package com.xeiam.xchange.streaming.websocket.drafts;

import com.xeiam.xchange.streaming.HandshakeData;
import com.xeiam.xchange.streaming.websocket.Draft;
import com.xeiam.xchange.streaming.websocket.HandshakeBuilder;
import com.xeiam.xchange.streaming.websocket.exeptions.InvalidHandshakeException;

public class Draft_17 extends Draft_10 {
  @Override
  public Draft.HandshakeState acceptHandshakeAsServer(HandshakeData handshakeData) throws InvalidHandshakeException {
    int v = readVersion(handshakeData);
    if (v == 13) {
      return Draft.HandshakeState.MATCHED;
    }
    return Draft.HandshakeState.NOT_MATCHED;
  }

  @Override
  public HandshakeBuilder postProcessHandshakeRequestAsClient(HandshakeBuilder request) {
    super.postProcessHandshakeRequestAsClient(request);
    request.put("Sec-WebSocket-Version", "13");// overwriting the previous
    return request;
  }

}
