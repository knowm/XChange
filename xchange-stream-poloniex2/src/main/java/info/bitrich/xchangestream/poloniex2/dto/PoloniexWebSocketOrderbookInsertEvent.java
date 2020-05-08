package info.bitrich.xchangestream.poloniex2.dto;

/** Created by Lukas Zaoralek on 11.11.17. */
public class PoloniexWebSocketOrderbookInsertEvent extends PoloniexWebSocketEvent {
  private final OrderbookInsertEvent insert;

  public PoloniexWebSocketOrderbookInsertEvent(OrderbookInsertEvent insert) {
    super("i");
    this.insert = insert;
  }

  public OrderbookInsertEvent getInsert() {
    return insert;
  }
}
