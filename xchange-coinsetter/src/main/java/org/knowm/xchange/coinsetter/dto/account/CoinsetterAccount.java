package org.knowm.xchange.coinsetter.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about a specified account.
 */
public class CoinsetterAccount {

  private final UUID accountUuid;
  private final UUID customerUuid;
  private final String accountNumber;
  private final String name;
  private final String description;
  private final BigDecimal btcBalance;
  private final BigDecimal usdBalance;
  private final String accountClass;
  private final String activeStatus;
  private final BigDecimal approvedMarginRatio;
  private final Date createDate;

  /**
   * @param accountUuid Account UUID
   * @param customerUuid Customer UUID
   * @param accountNumber Account #
   * @param name Name
   * @param description Description
   * @param btcBalance BTC Account Balance
   * @param usdBalance USD Account Balance
   * @param accountClass Acocunt class (i.e. "TEST", "VIRTUAL" or "LIVE")
   * @param activeStatus Status (i.e. "ACTIVE" or "INACTIVE")
   * @param approvedMarginRatio Your approved Margin Ratio
   * @param createDate Date/time account was created. Format = "dd/MM/yyyy HH:mm:ss.SSS"
   */
  public CoinsetterAccount(@JsonProperty("uuid") UUID accountUuid, @JsonProperty("customerUuid") UUID customerUuid,
      @JsonProperty("accountNumber") String accountNumber, @JsonProperty("name") String name, @JsonProperty("description") String description,
      @JsonProperty("btcBalance") BigDecimal btcBalance, @JsonProperty("usdBalance") BigDecimal usdBalance,
      @JsonProperty("accountClass") String accountClass, @JsonProperty("activeStatus") String activeStatus,
      @JsonProperty("approvedMarginRatio") BigDecimal approvedMarginRatio,
      @JsonProperty("createDate") @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "EST", locale = "us") Date createDate) {

    this.accountUuid = accountUuid;
    this.customerUuid = customerUuid;
    this.accountNumber = accountNumber;
    this.name = name;
    this.description = description;
    this.btcBalance = btcBalance;
    this.usdBalance = usdBalance;
    this.accountClass = accountClass;
    this.activeStatus = activeStatus;
    this.approvedMarginRatio = approvedMarginRatio;
    this.createDate = createDate;
  }

  public UUID getAccountUuid() {

    return accountUuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public String getAccountNumber() {

    return accountNumber;
  }

  public String getName() {

    return name;
  }

  public String getDescription() {

    return description;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getUsdBalance() {

    return usdBalance;
  }

  public String getAccountClass() {

    return accountClass;
  }

  public String getActiveStatus() {

    return activeStatus;
  }

  public BigDecimal getApprovedMarginRatio() {

    return approvedMarginRatio;
  }

  public Date getCreateDate() {

    return createDate;
  }

  @Override
  public String toString() {

    return "CoinsetterAccountResponse [accountUuid=" + accountUuid + ", customerUuid=" + customerUuid + ", accountNumber=" + accountNumber + ", name="
        + name + ", description=" + description + ", btcBalance=" + btcBalance + ", usdBalance=" + usdBalance + ", accountClass=" + accountClass
        + ", activeStatus=" + activeStatus + ", approvedMarginRatio=" + approvedMarginRatio + ", createDate=" + createDate + "]";
  }

}
