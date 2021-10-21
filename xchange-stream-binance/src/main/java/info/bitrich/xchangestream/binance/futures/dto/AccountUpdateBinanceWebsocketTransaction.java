package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<Balance> toBalanceList() {
    return updateData.getBalances().stream()
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
}
