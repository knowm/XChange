package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceWebsocketTransaction<T> {
  private String stream;
  private T data;

  public BinanceWebsocketTransaction(
      @JsonProperty("stream") String stream, @JsonProperty("data") T data) {
    this.stream = stream;
    this.data = data;
  }

  public String getStream() {
    return stream;
  }

  public T getData() {
    return data;
  }
}
