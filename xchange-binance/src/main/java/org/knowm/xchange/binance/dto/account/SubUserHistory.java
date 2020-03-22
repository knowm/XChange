package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;

public final class SubUserHistory {

  private String counterParty;
  private String email;
  private Integer type;
  private String asset;
  private BigDecimal qty;
  private long time;

  public String getCounterParty() {
    return counterParty;
  }

  public void setCounterParty(String counterParty) {
    this.counterParty = counterParty;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getAsset() {
    return asset;
  }

  public void setAsset(String asset) {
    this.asset = asset;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
