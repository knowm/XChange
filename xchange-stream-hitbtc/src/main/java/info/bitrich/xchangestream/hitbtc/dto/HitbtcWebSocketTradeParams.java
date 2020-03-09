package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketTradeParams extends HitbtcWebSocketBaseParams {

  private final HitbtcTrade[] data;

  public HitbtcWebSocketTradeParams(
      @JsonProperty("symbol") String symbol, @JsonProperty("params") HitbtcTrade[] data) {
    super(symbol);
    this.data = data;
  }

  public HitbtcTrade[] getData() {
    return data;
  }
}
