package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateData {

  private final String eventReasonType;
  private final List<BinanceFuturesWebsocketBalance> balances;

  public UpdateData(
      @JsonProperty("m") String eventReasonType,
      @JsonProperty("B") List<BinanceFuturesWebsocketBalance> balances) {
    this.eventReasonType = eventReasonType;
    this.balances = balances;
  }

  public String getEventReasonType() {
    return eventReasonType;
  }

  public List<BinanceFuturesWebsocketBalance> getBalances() {
    return balances;
  }
}
