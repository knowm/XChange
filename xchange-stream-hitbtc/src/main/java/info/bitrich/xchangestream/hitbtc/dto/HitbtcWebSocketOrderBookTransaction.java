package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketOrderBookTransaction extends HitbtcWebSocketBaseTransaction {

  private static final String ORDERBOOK_METHOD_UPDATE = "updateOrderbook";
  private HitbtcWebSocketOrderBookParams params;

  public HitbtcWebSocketOrderBookTransaction(
      @JsonProperty("method") String method,
      @JsonProperty("params") HitbtcWebSocketOrderBookParams params) {
    super(method);
    this.params = params;
  }

  public HitbtcWebSocketOrderBookParams getParams() {
    return params;
  }

  public HitbtcWebSocketOrderBook toHitbtcOrderBook(HitbtcWebSocketOrderBook orderbook) {
    if (method.equals(ORDERBOOK_METHOD_UPDATE)) {
      orderbook.updateOrderBook(this);
      return orderbook;
    }
    return new HitbtcWebSocketOrderBook(this);
  }
}
