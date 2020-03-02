package info.bitrich.xchangestream.poloniex2.dto;

/** Created by Lukas Zaoralek on 11.11.17. */
public class PoloniexWebSocketTradeEvent extends PoloniexWebSocketEvent {
  private final TradeEvent tradeEvent;

  public PoloniexWebSocketTradeEvent(TradeEvent tradeEvent) {
    super("t");
    this.tradeEvent = tradeEvent;
  }

  public TradeEvent getTradeEvent() {
    return tradeEvent;
  }
}
