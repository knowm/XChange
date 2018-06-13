package org.knowm.xchange.coingi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

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
}
