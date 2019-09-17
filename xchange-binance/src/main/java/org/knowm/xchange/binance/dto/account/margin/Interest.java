package org.knowm.xchange.binance.dto.account.margin;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

public class Interest {

  private Currency asset;
  private BigDecimal interest;
  private Long interestAccuredTime;
  private BigDecimal interestRate;
  private BigDecimal principal;
  private InterestType type;

  public Interest(
      String asset,
      BigDecimal interest,
      Long interestAccuredTime,
      BigDecimal interestRate,
      BigDecimal principal,
      String type) {
    this.asset = Currency.getInstance(asset);
    this.interest = interest;
    this.interestAccuredTime = interestAccuredTime;
    this.interestRate = interestRate;
    this.principal = principal;
    this.type = InterestType.valueOf(type);
  }

  public Currency getAsset() {
    return asset;
  }

  public void setAsset(Currency asset) {
    this.asset = asset;
  }

  public BigDecimal getInterest() {
    return interest;
  }

  public void setInterest(BigDecimal interest) {
    this.interest = interest;
  }

  public Long getInterestAccuredTime() {
    return interestAccuredTime;
  }

  public void setInterestAccuredTime(Long interestAccuredTime) {
    this.interestAccuredTime = interestAccuredTime;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public BigDecimal getPrincipal() {
    return principal;
  }

  public void setPrincipal(BigDecimal principal) {
    this.principal = principal;
  }

  public InterestType getType() {
    return type;
  }

  public void setType(InterestType type) {
    this.type = type;
  }
}
