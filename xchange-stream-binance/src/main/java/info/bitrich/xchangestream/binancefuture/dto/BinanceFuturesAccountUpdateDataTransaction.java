package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BinanceFuturesAccountUpdateDataTransaction {

  private final String eventReasonType;
  private final List<BinanceFuturesWebsocketBalancesTransaction> balances;
  private final List<BinanceFuturesWebsocketPositionsTransaction> positions;

  BinanceFuturesAccountUpdateDataTransaction(
      @JsonProperty("m") String eventReasonType,
      @JsonProperty("B") List<BinanceFuturesWebsocketBalancesTransaction> balances,
      @JsonProperty("P") List<BinanceFuturesWebsocketPositionsTransaction> positions
  ) {
    this.eventReasonType = eventReasonType;
    this.balances = balances;
    this.positions = positions;
  }

  public String getEventReasonType() {
    return eventReasonType;
  }

  public List<BinanceFuturesWebsocketBalancesTransaction> getBalances() {
    return balances;
  }

  public List<BinanceFuturesWebsocketPositionsTransaction> getPositions() {
    return positions;
  }
}
