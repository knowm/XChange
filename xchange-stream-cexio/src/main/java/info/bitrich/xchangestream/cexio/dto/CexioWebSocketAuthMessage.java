package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.cexio.CexioStreamingRawService;

public class CexioWebSocketAuthMessage {

  @JsonProperty("e")
  private final String e = CexioStreamingRawService.AUTH;

  private final CexioWebSocketAuth auth;

  public CexioWebSocketAuthMessage(@JsonProperty("auth") CexioWebSocketAuth auth) {
    this.auth = auth;
  }

  public String getE() {
    return e;
  }

  public CexioWebSocketAuth getAuth() {
    return auth;
  }

  @Override
  public String toString() {
    return "CexioWebSocketAuthMessage{" + "e='" + e + '\'' + ", auth=" + auth + '}';
  }
}
