package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketLoginResponse {

  String apiKey;
  long authorizedSince;
  long connectedSince;
  boolean returnRateLimits;
  long serverTime;
}
