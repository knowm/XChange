package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketTickerTransaction extends HitbtcWebSocketBaseTransaction {

  private final Integer id;
  private final HitbtcTicker params;

  public HitbtcWebSocketTickerTransaction(
      @JsonProperty("method") String method,
      @JsonProperty("id") Integer id,
      @JsonProperty("params") HitbtcTicker params) {
    super(method);
    this.id = id;
    this.params = params;
  }

  public Integer getId() {
    return id;
  }

  public HitbtcTicker getParams() {
    return params;
  }
}
