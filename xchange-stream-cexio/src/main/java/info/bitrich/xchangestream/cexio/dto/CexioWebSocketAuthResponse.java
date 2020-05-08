package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioWebSocketAuthResponse {

  private final String e;
  private final CexioWebSocketData data;
  private final String ok;
  private final long timestamp;

  public CexioWebSocketAuthResponse(
      @JsonProperty("e") String e,
      @JsonProperty("data") CexioWebSocketData data,
      @JsonProperty("ok") String ok,
      @JsonProperty("timestamp") long timestamp) {
    this.e = e;
    this.data = data;
    this.ok = ok;
    this.timestamp = timestamp;
  }

  public String getE() {
    return e;
  }

  public CexioWebSocketData getData() {
    return data;
  }

  public String getOk() {
    return ok;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public boolean isSuccess() {
    return getOk().equals("ok");
  }

  @Override
  public String toString() {
    return "CexioWebSocketAuthResponse{"
        + "e='"
        + e
        + '\''
        + ", data="
        + data
        + ", ok='"
        + ok
        + '\''
        + ", timestamp="
        + timestamp
        + '}';
  }
}
