package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.knowm.xchange.binance.futures.BinanceFuturesAdapter.adaptInstrument;
import static org.knowm.xchange.binance.futures.BinanceFuturesAdapter.adaptPositionType;

public class AccountUpdateBinanceWebsocketTransaction
    extends BaseBinanceWebSocketTransaction {

  private final long transactionTime;
  private final UpdateData updateData;

  public AccountUpdateBinanceWebsocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transactionTime,
      @JsonProperty("a") UpdateData updateData) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.updateData = updateData;
  }

  public long getTransactionTime() {
    return transactionTime;
  }

  public UpdateDataReasonType getEventReasonType() {
    return updateData.getEventReasonType();
  }

  public List<BinanceFuturesWebsocketBalance> getBalances() {
    return updateData.getBalances();
  }

  public List<BinanceFuturesWebsocketPosition> getPositions() {
    return updateData.getPositions();
  }

  public List<Balance> toBalanceList() {
    if (getBalances() == null) return Collections.emptyList();
    return getBalances().stream()
        .map(
            b ->
                new Balance(
                    Currency.getInstance(b.getAsset()),
                    b.getWalletBalance(),
                    b.getWalletBalance(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    new Date(transactionTime)))
        .collect(Collectors.toList());
  }

  public List<OpenPosition> toPositionList() {
    if (getPositions() == null) return Collections.emptyList();
    return getPositions().stream()
            .map(p -> new OpenPosition.Builder()
                    .instrument(adaptInstrument(p.symbol))
                    .type(adaptPositionType(p.positionSide, p.positionAmt))
                    .size(p.positionAmt)
                    .price(p.entryPrice)
                    .timestamp(new Date(transactionTime))
                    .build())
            .collect(Collectors.toList());
  }
}
