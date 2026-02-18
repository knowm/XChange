package info.bitrich.xchangestream.binance.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BinanceWebsocketLoginPayloadWithSignature {
  private String apiKey;
  private String signature;
  private long timestamp;
}
