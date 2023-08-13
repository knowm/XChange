package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;

public class BinanceFuturesOrderUpdateTransaction extends BaseBinanceWebSocketTransaction {

  private final long transaction;
  private final BinanceFuturesWebsocketOrderTransaction order;

  BinanceFuturesOrderUpdateTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transaction,
      @JsonProperty("o") BinanceFuturesWebsocketOrderTransaction order
  ) {
    super(eventType, eventTime);
    this.transaction = transaction;
    this.order = order;
  }

  public long getTransaction() {
    return transaction;
  }

  public BinanceFuturesWebsocketOrderTransaction getOrder() {
    return order;
  }
}
