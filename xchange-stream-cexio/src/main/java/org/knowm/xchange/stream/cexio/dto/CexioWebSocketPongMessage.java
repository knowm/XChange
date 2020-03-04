package org.knowm.xchange.stream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.stream.cexio.CexioStreamingRawService;

public class CexioWebSocketPongMessage {

  @JsonProperty("e")
  private final String e = CexioStreamingRawService.PONG;

  public CexioWebSocketPongMessage() {}

  public String getE() {
    return e;
  }

  @Override
  public String toString() {
    return "CexioWebSocketPongMessage{" + "e='" + e + '\'' + '}';
  }
}
