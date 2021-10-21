package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import org.knowm.xchange.dto.account.Balance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceFuturesWebsocketBalance {

  private final String asset;
  private final BigDecimal walletBalance;
  private final BigDecimal crossWalletBalance;
  private final BigDecimal balanceChange;

  public BinanceFuturesWebsocketBalance(
          @JsonProperty("a") String asset,
          @JsonProperty("wb") BigDecimal walletBalance,
          @JsonProperty("cw") BigDecimal crossWalletBalance,
          @JsonProperty("bc") BigDecimal balanceChange) {
    this.asset = asset;
    this.walletBalance = walletBalance;
    this.crossWalletBalance = crossWalletBalance;
    this.balanceChange = balanceChange;
  }

  public String getAsset() {
    return asset;
  }

  public BigDecimal getWalletBalance() {
    return walletBalance;
  }

  public BigDecimal getCrossWalletBalance() {
    return crossWalletBalance;
  }

  public BigDecimal getBalanceChange() {
    return balanceChange;
  }
}
