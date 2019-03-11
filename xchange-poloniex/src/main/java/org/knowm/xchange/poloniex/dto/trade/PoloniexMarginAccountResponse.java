package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PoloniexMarginAccountResponse {

  private BigDecimal totalValue;
  private BigDecimal pl;
  private BigDecimal lendingFees;
  private BigDecimal netValue;
  private BigDecimal totalBorrowedValue;
  private BigDecimal currentMargin;

  @JsonCreator
  public PoloniexMarginAccountResponse(
      @JsonProperty("totalValue") BigDecimal totalValue,
      @JsonProperty("pl") BigDecimal pl,
      @JsonProperty("lendingFees") BigDecimal lendingFees,
      @JsonProperty("netValue") BigDecimal netValue,
      @JsonProperty("totalBorrowedValue") BigDecimal totalBorrowedValue,
      @JsonProperty("currentMargin") BigDecimal currentMargin) {
    this.totalValue = totalValue;
    this.pl = pl;
    this.lendingFees = lendingFees;
    this.netValue = netValue;
    this.totalBorrowedValue = totalBorrowedValue;
    this.currentMargin = currentMargin;
  }

  public BigDecimal getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(BigDecimal totalValue) {
    this.totalValue = totalValue;
  }

  public BigDecimal getPl() {
    return pl;
  }

  public void setPl(BigDecimal pl) {
    this.pl = pl;
  }

  public BigDecimal getLendingFees() {
    return lendingFees;
  }

  public void setLendingFees(BigDecimal lendingFees) {
    this.lendingFees = lendingFees;
  }

  public BigDecimal getNetValue() {
    return netValue;
  }

  public void setNetValue(BigDecimal netValue) {
    this.netValue = netValue;
  }

  public BigDecimal getTotalBorrowedValue() {
    return totalBorrowedValue;
  }

  public void setTotalBorrowedValue(BigDecimal totalBorrowedValue) {
    this.totalBorrowedValue = totalBorrowedValue;
  }

  public BigDecimal getCurrentMargin() {
    return currentMargin;
  }

  public void setCurrentMargin(BigDecimal currentMargin) {
    this.currentMargin = currentMargin;
  }

  @Override
  public String toString() {
    return "PoloniexMarginAccountResponse{"
        + "totalValue="
        + totalValue
        + ", pl="
        + pl
        + ", lendingFees="
        + lendingFees
        + ", netValue="
        + netValue
        + ", totalBorrowedValue="
        + totalBorrowedValue
        + ", currentMargin="
        + currentMargin
        + '}';
  }
}
