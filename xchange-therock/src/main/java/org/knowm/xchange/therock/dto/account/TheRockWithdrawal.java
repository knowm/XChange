package org.knowm.xchange.therock.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TheRockWithdrawal {

  private String currency;

  /**
   * Should be null for the default method (ie. not Ripple)
   */
  private Method withdrawMethod;

  private String destinationAddress;

  private Long destinationTag = null;

  private BigDecimal amount;

  private TheRockWithdrawal(String currency, BigDecimal amount, String destinationAddress) {
    this(currency, amount, destinationAddress, null, null);
  }

  private TheRockWithdrawal(String currency, BigDecimal amount, String destinationAddress, Method withdrawMethod, Long destinationTag) {
    this.currency = currency;
    this.amount = amount;
    this.destinationAddress = destinationAddress;
    this.withdrawMethod = withdrawMethod;
    this.destinationTag = destinationTag;
  }

  public static TheRockWithdrawal createRippleWithdrawal(String currency, BigDecimal amount, String destinationAddress, Long destinationTag) {
    return new TheRockWithdrawal(currency, amount, destinationAddress, Method.RIPPLE, destinationTag);
  }

  public static TheRockWithdrawal createDefaultWithdrawal(String currency, BigDecimal amount, String destinationAddress) {
    return new TheRockWithdrawal(currency, amount, destinationAddress);
  }

  public String getCurrency() {
    return currency;
  }

  public Method getWithdrawMethod() {
    return withdrawMethod;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public Long getDestinationTag() {
    return destinationTag;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return String.format("TheRockWithdrawal{currency='%s', withdrawMethod='%s', destinationAddress='%s', amount=%s}", currency,
        withdrawMethod == null ? "<default>" : withdrawMethod, destinationAddress, amount);
  }

  public enum Method {
    RIPPLE
  }
}
