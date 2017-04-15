package org.knowm.xchange.independentreserve.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAccount {
  private final String accountGuid;
  private final String accountStatus;
  private final BigDecimal availableBalance;
  private final String currencyCode;
  private final BigDecimal totalBalance;

  public IndependentReserveAccount(@JsonProperty("AccountGuid") String accountGuid, @JsonProperty("AccountStatus") String accountStatus,
      @JsonProperty("AvailableBalance") BigDecimal availableBalance, @JsonProperty("CurrencyCode") String currencyCode,
      @JsonProperty("TotalBalance") BigDecimal totalBalance) {
    this.accountGuid = accountGuid;
    this.accountStatus = accountStatus;
    this.availableBalance = availableBalance;
    this.currencyCode = currencyCode;
    this.totalBalance = totalBalance;
  }

  public String getAccountGuid() {
    return accountGuid;
  }

  public String getAccountStatus() {
    return accountStatus;
  }

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public BigDecimal getTotalBalance() {
    return totalBalance;
  }

  @Override
  public String toString() {
    return "IndependentReserveAccount [accountGuid=" + accountGuid + ", accountStatus=" + accountStatus + ", availableBalance=" + availableBalance
        + ", currencyCode=" + currencyCode + ", totalBalance=" + totalBalance + "]";
  }

}
