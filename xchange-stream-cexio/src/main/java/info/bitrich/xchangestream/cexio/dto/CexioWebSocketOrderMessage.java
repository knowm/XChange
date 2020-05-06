package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioWebSocketOrderMessage {

  private final String e;
  private final CexioWebSocketOrder data;

  public CexioWebSocketOrderMessage(
      @JsonProperty("e") String e, @JsonProperty("data") CexioWebSocketOrder data) {
    this.e = e;
    this.data = data;
  }

  public String getE() {
    return e;
  }

  public CexioWebSocketOrder getData() {
    return data;
  }

  @Override
  public String toString() {
    return "CexioWebSocketOrderMessage {" + "e='" + e + '\'' + ", data=" + data + '}';
  }
}
