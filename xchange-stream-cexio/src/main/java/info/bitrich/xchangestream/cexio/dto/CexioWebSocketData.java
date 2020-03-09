package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioWebSocketData {

  private final String error;
  private final String ok;

  public CexioWebSocketData(@JsonProperty("error") String error, @JsonProperty("ok") String ok) {
    this.error = error;
    this.ok = ok;
  }

  public String getError() {
    return error;
  }

  public String getOk() {
    return ok;
  }

  @Override
  public String toString() {
    return "CexioWebSocketData{" + "error='" + error + '\'' + ", ok='" + ok + '\'' + '}';
  }
}
