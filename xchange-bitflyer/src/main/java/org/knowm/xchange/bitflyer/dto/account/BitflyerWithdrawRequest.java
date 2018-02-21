package org.knowm.xchange.bitflyer.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Object representing body of request <code>POST /v1/me/withdraw</code>
 *  
 * <p>Example</p>
 * {
 *   "currency_code": "JPY",
 *   "bank_account_id": 1234,
 *   "amount": 12000
 * }
 * @author bryant_harris
 */
public class BitflyerWithdrawRequest {
  @JsonProperty("currency_code")
  private String currencyCode;
  
  @JsonProperty("bank_account_id")
  private String bankAccountID;
  
  @JsonProperty("amount")
  private BigDecimal amount;

  public BitflyerWithdrawRequest(String currencyCode, String bankAccountID, BigDecimal amount) {
    super();
    this.currencyCode = currencyCode;
    this.bankAccountID = bankAccountID;
    this.amount = amount;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getBankAccountID() {
    return bankAccountID;
  }

  public void setBankAccountID(String bankAccountID) {
    this.bankAccountID = bankAccountID;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "BitflyerWithdrawRequest [currencyCode=" + currencyCode + ", bankAccountID=" + bankAccountID + ", amount="
        + amount + "]";
  }  
}
