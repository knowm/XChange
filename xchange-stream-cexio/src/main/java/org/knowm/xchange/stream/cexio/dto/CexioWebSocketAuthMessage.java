package org.knowm.xchange.stream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.stream.cexio.CexioStreamingRawService;

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
