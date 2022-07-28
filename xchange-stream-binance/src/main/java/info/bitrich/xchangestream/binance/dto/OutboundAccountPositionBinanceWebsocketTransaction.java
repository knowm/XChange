package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OutboundAccountPositionBinanceWebsocketTransaction
    extends BaseBinanceWebSocketTransaction {

  private final long lastUpdateTimestamp;
  private final List<BinanceWebsocketBalance> balances;


  public OutboundAccountPositionBinanceWebsocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("u") long lastUpdateTimestamp,
      @JsonProperty("B") List<BinanceWebsocketBalance> balances
  ) {
    super(eventType, eventTime);
    this.lastUpdateTimestamp = lastUpdateTimestamp;
    this.balances = balances;
  }


  public long getLastUpdateTimestamp() {
    return lastUpdateTimestamp;
  }

  public List<BinanceWebsocketBalance> getBalances() {
    return balances;
  }

  public List<Balance> toBalanceList() {
    return balances.stream()
            .map(
                    b ->
                            new Balance(
                                    b.getCurrency(),
                                    b.getTotal(),
                                    b.getAvailable(),
                                    b.getLocked(),
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    new Date(lastUpdateTimestamp)))
            .collect(Collectors.toList());
  }


  @Override
  public String toString() {
    return "OutboundAccountInfoBinanceWebsocketTransaction [ lastUpdateTimestamp="
        + lastUpdateTimestamp
        + ", balances="
        + balances
        + "]";
  }
}
