package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceFuturesAccountUpdateTransaction extends BaseBinanceWebSocketTransaction {

  private final long transaction;
  private final BinanceFuturesAccountUpdateDataTransaction updateData;

  BinanceFuturesAccountUpdateTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transaction,
      @JsonProperty("a") BinanceFuturesAccountUpdateDataTransaction updateData
  ) {
    super(eventType, eventTime);
    this.transaction = transaction;
    this.updateData = updateData;
  }

  public long getTransaction() {
    return transaction;
  }

  public BinanceFuturesAccountUpdateDataTransaction getUpdateData() {
    return updateData;
  }

  public List<BinanceFuturesBalance> toBalanceList() {
    return updateData
        .getBalances()
        .stream()
        .map(BinanceFuturesBalance::new)
        .collect(Collectors.toList());
  }

  public List<BinanceFuturesPosition> toPositionList() {
    return updateData
        .getPositions()
        .stream()
        .map(BinanceFuturesPosition::new)
        .collect(Collectors.toList());
  }
}
