package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BinanceFuturesWebsocketBalancesTransaction {

  private final String asset;
  private final BigDecimal walletBalance;
  private final BigDecimal crossWalletBalance;
  private final BigDecimal balanceChange;

  BinanceFuturesWebsocketBalancesTransaction(
      @JsonProperty("a") String asset,
      @JsonProperty("wb") BigDecimal walletBalance,
      @JsonProperty("cw") BigDecimal crossWalletBalance,
      @JsonProperty("bc") BigDecimal balanceChange
  ) {
    this.asset = asset;
    this.walletBalance = walletBalance;
    this.crossWalletBalance = crossWalletBalance;
    this.balanceChange = balanceChange;
  }


  public BigDecimal getBalanceChange() {
    return balanceChange;
  }

  public BigDecimal getCrossWalletBalance() {
    return crossWalletBalance;
  }

  public BigDecimal getWalletBalance() {
    return walletBalance;
  }

  public String getAsset() {
    return asset;
  }
}
