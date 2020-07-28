package info.bitrich.xchangestream.poloniex2.dto;

/** Created by Lukas Zaoralek on 11.11.17. */
public class PoloniexWebSocketOrderbookModifiedEvent extends PoloniexWebSocketEvent {
  private final OrderbookModifiedEvent modifiedEvent;

  public PoloniexWebSocketOrderbookModifiedEvent(OrderbookModifiedEvent modifiedEvent) {
    super("o");
    this.modifiedEvent = modifiedEvent;
  }

  public OrderbookModifiedEvent getModifiedEvent() {
    return modifiedEvent;
  }
}
