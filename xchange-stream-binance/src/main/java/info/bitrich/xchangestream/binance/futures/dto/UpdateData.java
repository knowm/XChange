package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateData {

  private final UpdateDataReasonType eventReasonType;
  private final List<BinanceFuturesWebsocketBalance> balances;
  private final List<BinanceFuturesWebsocketPosition> positions;

  public UpdateData(
      @JsonProperty("m") UpdateDataReasonType eventReasonType,
      @JsonProperty("B") List<BinanceFuturesWebsocketBalance> balances,
      @JsonProperty("P") List<BinanceFuturesWebsocketPosition> positions) {
    this.eventReasonType = eventReasonType;
    this.balances = balances;
    this.positions = positions;
  }

  public UpdateDataReasonType getEventReasonType() {
    return eventReasonType;
  }

  public List<BinanceFuturesWebsocketBalance> getBalances() {
    return balances;
  }

  public List<BinanceFuturesWebsocketPosition> getPositions() {
    return positions;
  }
}
