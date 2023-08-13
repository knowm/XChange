package info.bitrich.xchangestream.binancefuture.dto;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

public class BinanceFuturesBalance {

  private final Currency currency;
  private final BigDecimal walletBalance;
  private final BigDecimal crossWalletBalance;
  private final BigDecimal balanceChange;

  public BinanceFuturesBalance(BinanceFuturesWebsocketBalancesTransaction balance) {
    this.currency = new Currency(balance.getAsset());
    this.walletBalance = balance.getWalletBalance();
    this.crossWalletBalance = balance.getCrossWalletBalance();
    this.balanceChange = balance.getBalanceChange();
  }


  public Currency getCurrency() {
    return currency;
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
