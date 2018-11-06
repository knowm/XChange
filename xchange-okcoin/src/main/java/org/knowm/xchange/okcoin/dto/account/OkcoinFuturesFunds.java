package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkcoinFuturesFunds {

  private final BigDecimal available;
  private final BigDecimal balance;
  private final BigDecimal bond;
  private final Long contractId;
  private final String contratctType;
  private final BigDecimal freeze;
  private final Integer profit;
  private final Integer unprofit;

  public OkcoinFuturesFunds(
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("bond") BigDecimal bond,
      @JsonProperty("contract_id") Long contractId,
      @JsonProperty("contratct_type") String contratctType,
      @JsonProperty("freeze") BigDecimal freeze,
      @JsonProperty("profit") Integer profit,
      @JsonProperty("unprofit") Integer unprofit) {
    this.available = available;
    this.balance = balance;
    this.bond = bond;
    this.contractId = contractId;
    this.contratctType = contratctType;
    this.freeze = freeze;
    this.profit = profit;
    this.unprofit = unprofit;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getBond() {
    return bond;
  }

  public Long getContractId() {
    return contractId;
  }

  public String getContratctType() {
    return contratctType;
  }

  public BigDecimal getFreeze() {
    return freeze;
  }

  public Integer getProfit() {
    return profit;
  }

  public Integer getUnprofit() {
    return unprofit;
  }
}
