package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketOrderResponse<T> {
  String id;
  int status;
  T result;
  BinanceError error;

  @Value
  @NoArgsConstructor(force = true)
  public static class BinanceError {
    int code;
    String msg;
  }
}
