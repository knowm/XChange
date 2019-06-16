package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.cexio.CexioStreamingRawService;

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
