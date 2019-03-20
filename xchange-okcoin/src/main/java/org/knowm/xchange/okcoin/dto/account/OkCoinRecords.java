package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OkCoinRecords {

  private final String address;

  private final String account;

  private final BigDecimal amount;

  private final String bank;

  private final String benificiaryAddress;

  private final BigDecimal transactionValue;

  private final BigDecimal fee;

  private final Long date;

  private final Integer status;

  public OkCoinRecords(
      @JsonProperty("addr") final String address,
      @JsonProperty("account") final String account,
      @JsonProperty("amount") final BigDecimal amount,
      @JsonProperty("bank") final String bank,
      @JsonProperty("benificiary_addr") final String benificiaryAddress,
      @JsonProperty("transaction_value") final BigDecimal transactionValue,
      @JsonProperty("fee") final BigDecimal fee,
      @JsonProperty("date") final Long date,
      @JsonProperty("status") final Integer status) {

    this.address = address;
    this.account = account;
    this.amount = amount;
    this.bank = bank;
    this.benificiaryAddress = benificiaryAddress;
    this.transactionValue = transactionValue;
    this.fee = fee;
    this.date = date;
    this.status = status;
  }

  public String getAddress() {

    return address;
  }

  public String getAccount() {

    return account;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getBank() {

    return bank;
  }

  public String getBenificiaryAddress() {

    return benificiaryAddress;
  }

  public BigDecimal getTransactionValue() {

    return transactionValue;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public Long getDate() {

    return date;
  }

  public Integer getStatus() {
    return status;
  }

  public enum RechargeStatus {
    FAILURE(-1, "Failure"),
    WAIT_CONFIRMATION(0, "Wait Confirmation"),
    CONFIRMATION_ACCOUNT(1, "Confirmation Account;"),
    RECHARGE_SUCCESS(2, "Complete")
    ;

    private static final Map<Integer, RechargeStatus> fromInt =
        new HashMap<Integer, RechargeStatus>();

    static {
      for (RechargeStatus status : values()) fromInt.put(status.code, status);
    }

    private int code;
    private String status;

    RechargeStatus(int code, String status) {
      this.code = code;
      this.status = status;
    }

    public static RechargeStatus fromInt(int statusInt) {
      return fromInt.get(statusInt);
    }

    public String getStatus() {
      return status;
    }
  }

  public enum WithdrawalStatus {
    REVOKED(-3, "Revoked"),
    CANCELLED(-2, "Cancelled"),
    FAILURE(-1, "Failure"),
    PENDING_0(0, "Pending"),
    PENDING_1(1, "Pending"),
    COMPLETE(2, "Complete"),
    EMAIL_CONFIRMATION(3, "Email Confirmation"),
    VERIFYING(4, "Verifying"),
    WAIT_CONFIRMATION(5, "Wait Confirmation"),
    ;

    private static final Map<Integer, WithdrawalStatus> fromInt =
        new HashMap<Integer, WithdrawalStatus>();

    static {
      for (WithdrawalStatus status : values()) fromInt.put(status.code, status);
    }

    private int code;
    private String status;

    WithdrawalStatus(int code, String status) {
      this.code = code;
      this.status = status;
    }

    public static WithdrawalStatus fromInt(int statusInt) {
      return fromInt.get(statusInt);
    }

    public String getStatus() {
      return status;
    }
  }
}
