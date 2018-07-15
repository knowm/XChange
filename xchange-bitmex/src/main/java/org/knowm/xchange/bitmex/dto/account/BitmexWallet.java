package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "account",
  "currency",
  "prevDeposited",
  "prevWithdrawn",
  "prevTransferIn",
  "prevTransferOut",
  "prevAmount",
  "prevTimestamp",
  "deltaDeposited",
  "deltaWithdrawn",
  "deltaTransferIn",
  "deltaTransferOut",
  "deltaAmount",
  "deposited",
  "withdrawn",
  "transferIn",
  "transferOut",
  "amount",
  "pendingCredit",
  "pendingDebit",
  "confirmedDebit",
  "timestamp",
  "addr",
  "script",
  "withdrawalLock"
})
public final class BitmexWallet extends AbstractHttpResponseAware {

  @JsonProperty("account")
  private Integer account;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("prevDeposited")
  private BigDecimal prevDeposited;

  @JsonProperty("prevWithdrawn")
  private BigDecimal prevWithdrawn;

  @JsonProperty("prevTransferIn")
  private BigDecimal prevTransferIn;

  @JsonProperty("prevTransferOut")
  private BigDecimal prevTransferOut;

  @JsonProperty("prevAmount")
  private BigDecimal prevAmount;

  @JsonProperty("prevTimestamp")
  private String prevTimestamp;

  @JsonProperty("deltaDeposited")
  private BigDecimal deltaDeposited;

  @JsonProperty("deltaWithdrawn")
  private BigDecimal deltaWithdrawn;

  @JsonProperty("deltaTransferIn")
  private BigDecimal deltaTransferIn;

  @JsonProperty("deltaTransferOut")
  private BigDecimal deltaTransferOut;

  @JsonProperty("deltaAmount")
  private BigDecimal deltaAmount;

  @JsonProperty("deposited")
  private BigDecimal deposited;

  @JsonProperty("withdrawn")
  private BigDecimal withdrawn;

  @JsonProperty("transferIn")
  private BigDecimal transferIn;

  @JsonProperty("transferOut")
  private BigDecimal transferOut;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("pendingCredit")
  private BigDecimal pendingCredit;

  @JsonProperty("pendingDebit")
  private BigDecimal pendingDebit;

  @JsonProperty("confirmedDebit")
  private BigDecimal confirmedDebit;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("addr")
  private String addr;

  @JsonProperty("script")
  private String script;

  @JsonProperty("withdrawalLock")
  private List<String> withdrawalLock = null;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public Integer getAccount() {
    return account;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getPrevDeposited() {
    return prevDeposited;
  }

  public BigDecimal getPrevWithdrawn() {
    return prevWithdrawn;
  }

  public BigDecimal getPrevTransferIn() {
    return prevTransferIn;
  }

  public BigDecimal getPrevTransferOut() {
    return prevTransferOut;
  }

  public BigDecimal getPrevAmount() {
    return prevAmount;
  }

  public String getPrevTimestamp() {
    return prevTimestamp;
  }

  public BigDecimal getDeltaDeposited() {
    return deltaDeposited;
  }

  public BigDecimal getDeltaWithdrawn() {
    return deltaWithdrawn;
  }

  public BigDecimal getDeltaTransferIn() {
    return deltaTransferIn;
  }

  public BigDecimal getDeltaTransferOut() {
    return deltaTransferOut;
  }

  public BigDecimal getDeltaAmount() {
    return deltaAmount;
  }

  public BigDecimal getDeposited() {
    return deposited;
  }

  public BigDecimal getWithdrawn() {
    return withdrawn;
  }

  public BigDecimal getTransferIn() {
    return transferIn;
  }

  public BigDecimal getTransferOut() {
    return transferOut;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPendingCredit() {
    return pendingCredit;
  }

  public BigDecimal getPendingDebit() {
    return pendingDebit;
  }

  public BigDecimal getConfirmedDebit() {
    return confirmedDebit;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getAddr() {
    return addr;
  }

  public String getScript() {
    return script;
  }

  public List<String> getWithdrawalLock() {
    return withdrawalLock;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
