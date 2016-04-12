package org.knowm.xchange.btcchina.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaDeposit {

  /**
   * Deposit id.
   */
  private final long id;

  /**
   * Bitcoin/Litecoin wallet address.
   */
  private final String address;

  /**
   * [ BTC | LTC ]
   */
  private final String currency;

  /**
   * Total amount.
   */
  private final BigDecimal amount;

  /**
   * Unix time in seconds since 1 January 1970.
   */
  private final long date;

  /**
   * [ pending | completed ]
   */
  private final String status;

  public BTCChinaDeposit(@JsonProperty("id") long id, @JsonProperty("address") String address, @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("status") String status) {

    this.id = id;
    this.address = address;
    this.currency = currency;
    this.amount = amount;
    this.date = date;
    this.status = status;
  }

  public long getId() {

    return id;
  }

  public String getAddress() {

    return address;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return "BTCChinaDeposit [id=" + id + ", address=" + address + ", currency=" + currency + ", amount=" + amount + ", date=" + date + ", status="
        + status + "]";
  }

}
