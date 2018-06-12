package org.knowm.xchange.coingi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class CoingiBalance {
  private Map<String, Object> currency;

  private BigDecimal available;

  private BigDecimal inOrders;

  private BigDecimal deposited;

  private BigDecimal withdrawing;

  private BigDecimal blocked;

  public CoingiBalance(
      @JsonProperty("currency") Map<String, Object> currency,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("inOrders") BigDecimal inOrders,
      @JsonProperty("deposited") BigDecimal deposited,
      @JsonProperty("withdrawing") BigDecimal withdrawing,
      @JsonProperty("blocked") BigDecimal blocked) {

    this.currency = currency;
    this.available = available;
    this.inOrders = inOrders;
    this.deposited = deposited;
    this.withdrawing = withdrawing;
    this.blocked = blocked;
  }

  CoingiBalance() {}

  public Map<String, Object> getCurrency() {
    return currency;
  }

  public String getCurrencyName() {
    return (String) currency.get("name");
  }

  public boolean getCurrencyCrypto() {
    return (Boolean) currency.get("crypto");
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getInOrders() {
    return inOrders;
  }

  public BigDecimal getDeposited() {
    return deposited;
  }

  public BigDecimal getWithdrawing() {
    return withdrawing;
  }

  public BigDecimal getBlocked() {
    return blocked;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CoingiBalance that = (CoingiBalance) o;
    return Objects.equals(currency, that.currency)
        && Objects.equals(available, that.available)
        && Objects.equals(inOrders, that.inOrders)
        && Objects.equals(deposited, that.deposited)
        && Objects.equals(withdrawing, that.withdrawing)
        && Objects.equals(blocked, that.blocked);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, available, inOrders, deposited, withdrawing, blocked);
  }
}
