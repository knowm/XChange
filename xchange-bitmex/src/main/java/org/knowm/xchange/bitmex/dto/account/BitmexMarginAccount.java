package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "account",
  "currency",
  "riskLimit",
  "prevState",
  "state",
  "action",
  "amount",
  "pendingCredit",
  "pendingDebit",
  "confirmedDebit",
  "prevRealisedPnl",
  "prevUnrealisedPnl",
  "grossComm",
  "grossOpenCost",
  "grossOpenPremium",
  "grossExecCost",
  "grossMarkValue",
  "riskValue",
  "taxableMargin",
  "initMargin",
  "maintMargin",
  "sessionMargin",
  "targetExcessMargin",
  "varMargin",
  "realisedPnl",
  "unrealisedPnl",
  "indicativeTax",
  "unrealisedProfit",
  "syntheticMargin",
  "walletBalance",
  "marginBalance",
  "marginBalancePcnt",
  "marginLeverage",
  "marginUsedPcnt",
  "excessMargin",
  "excessMarginPcnt",
  "availableMargin",
  "withdrawableMargin",
  "timestamp",
  "grossLastValue",
  "commission"
})
public final class BitmexMarginAccount extends AbstractHttpResponseAware {

  @JsonProperty("account")
  public Integer account;

  @JsonProperty("currency")
  public String currency;

  @JsonProperty("riskLimit")
  public BigDecimal riskLimit;

  @JsonProperty("prevState")
  public String prevState;

  @JsonProperty("state")
  public String state;

  @JsonProperty("action")
  public String action;

  @JsonProperty("amount")
  public BigDecimal amount;

  @JsonProperty("pendingCredit")
  public BigDecimal pendingCredit;

  @JsonProperty("pendingDebit")
  public BigDecimal pendingDebit;

  @JsonProperty("confirmedDebit")
  public BigDecimal confirmedDebit;

  @JsonProperty("prevRealisedPnl")
  public BigDecimal prevRealisedPnl;

  @JsonProperty("prevUnrealisedPnl")
  public BigDecimal prevUnrealisedPnl;

  @JsonProperty("grossComm")
  public BigDecimal grossComm;

  @JsonProperty("grossOpenCost")
  public BigDecimal grossOpenCost;

  @JsonProperty("grossOpenPremium")
  public BigDecimal grossOpenPremium;

  @JsonProperty("grossExecCost")
  public BigDecimal grossExecCost;

  @JsonProperty("grossMarkValue")
  public BigDecimal grossMarkValue;

  @JsonProperty("riskValue")
  public BigDecimal riskValue;

  @JsonProperty("taxableMargin")
  public BigDecimal taxableMargin;

  @JsonProperty("initMargin")
  public BigDecimal initMargin;

  @JsonProperty("maintMargin")
  public BigDecimal maintMargin;

  @JsonProperty("sessionMargin")
  public BigDecimal sessionMargin;

  @JsonProperty("targetExcessMargin")
  public BigDecimal targetExcessMargin;

  @JsonProperty("varMargin")
  public BigDecimal varMargin;

  @JsonProperty("realisedPnl")
  public BigDecimal realisedPnl;

  @JsonProperty("unrealisedPnl")
  public BigDecimal unrealisedPnl;

  @JsonProperty("indicativeTax")
  public BigDecimal indicativeTax;

  @JsonProperty("unrealisedProfit")
  public BigDecimal unrealisedProfit;

  @JsonProperty("syntheticMargin")
  public BigDecimal syntheticMargin;

  @JsonProperty("walletBalance")
  public BigDecimal walletBalance;

  @JsonProperty("marginBalance")
  public BigDecimal marginBalance;

  @JsonProperty("marginBalancePcnt")
  public BigDecimal marginBalancePcnt;

  @JsonProperty("marginLeverage")
  public BigDecimal marginLeverage;

  @JsonProperty("marginUsedPcnt")
  public BigDecimal marginUsedPcnt;

  @JsonProperty("excessMargin")
  public BigDecimal excessMargin;

  @JsonProperty("excessMarginPcnt")
  public BigDecimal excessMarginPcnt;

  @JsonProperty("availableMargin")
  public BigDecimal availableMargin;

  @JsonProperty("withdrawableMargin")
  public BigDecimal withdrawableMargin;

  @JsonProperty("timestamp")
  public String timestamp;

  @JsonProperty("grossLastValue")
  public BigDecimal grossLastValue;

  @JsonProperty("commission")
  public BigDecimal commission;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public Integer getAccount() {
    return account;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getRiskLimit() {
    return riskLimit;
  }

  public String getPrevState() {
    return prevState;
  }

  public String getState() {
    return state;
  }

  public String getAction() {
    return action;
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

  public BigDecimal getPrevRealisedPnl() {
    return prevRealisedPnl;
  }

  public BigDecimal getPrevUnrealisedPnl() {
    return prevUnrealisedPnl;
  }

  public BigDecimal getGrossComm() {
    return grossComm;
  }

  public BigDecimal getGrossOpenCost() {
    return grossOpenCost;
  }

  public BigDecimal getGrossOpenPremium() {
    return grossOpenPremium;
  }

  public BigDecimal getGrossExecCost() {
    return grossExecCost;
  }

  public BigDecimal getGrossMarkValue() {
    return grossMarkValue;
  }

  public BigDecimal getRiskValue() {
    return riskValue;
  }

  public BigDecimal getTaxableMargin() {
    return taxableMargin;
  }

  public BigDecimal getInitMargin() {
    return initMargin;
  }

  public BigDecimal getMaintMargin() {
    return maintMargin;
  }

  public BigDecimal getSessionMargin() {
    return sessionMargin;
  }

  public BigDecimal getTargetExcessMargin() {
    return targetExcessMargin;
  }

  public BigDecimal getVarMargin() {
    return varMargin;
  }

  public BigDecimal getRealisedPnl() {
    return realisedPnl;
  }

  public BigDecimal getUnrealisedPnl() {
    return unrealisedPnl;
  }

  public BigDecimal getIndicativeTax() {
    return indicativeTax;
  }

  public BigDecimal getUnrealisedProfit() {
    return unrealisedProfit;
  }

  public BigDecimal getSyntheticMargin() {
    return syntheticMargin;
  }

  public BigDecimal getWalletBalance() {
    return walletBalance;
  }

  public BigDecimal getMarginBalance() {
    return marginBalance;
  }

  public BigDecimal getMarginBalancePcnt() {
    return marginBalancePcnt;
  }

  public BigDecimal getMarginLeverage() {
    return marginLeverage;
  }

  public BigDecimal getMarginUsedPcnt() {
    return marginUsedPcnt;
  }

  public BigDecimal getExcessMargin() {
    return excessMargin;
  }

  public BigDecimal getExcessMarginPcnt() {
    return excessMarginPcnt;
  }

  public BigDecimal getAvailableMargin() {
    return availableMargin;
  }

  public BigDecimal getWithdrawableMargin() {
    return withdrawableMargin;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getGrossLastValue() {
    return grossLastValue;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
