package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderLimit;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketOrderBookParams extends HitbtcWebSocketBaseParams {

  private final HitbtcOrderLimit[] ask;
  private final HitbtcOrderLimit[] bid;
  private final long sequence;

  public HitbtcWebSocketOrderBookParams(
      @JsonProperty("property") String symbol,
      @JsonProperty("sequence") long sequence,
      @JsonProperty("ask") HitbtcOrderLimit[] ask,
      @JsonProperty("bid") HitbtcOrderLimit[] bid) {
    super(symbol);
    this.ask = ask;
    this.bid = bid;
    this.sequence = sequence;
  }

  public HitbtcOrderLimit[] getAsk() {
    return ask;
  }

  public HitbtcOrderLimit[] getBid() {
    return bid;
  }

  public long getSequence() {
    return sequence;
  }
}
