package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketPayload<T> {

  String id;
  String method;
  T params;
}
