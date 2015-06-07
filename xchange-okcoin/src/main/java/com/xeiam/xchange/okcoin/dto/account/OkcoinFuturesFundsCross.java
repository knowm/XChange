package com.xeiam.xchange.okcoin.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;



public class OkcoinFuturesFundsCross {

  private final BigDecimal accountRights;
  private final BigDecimal keepDeposits;
  private final BigDecimal profitReal;
  private final BigDecimal profitUnreal;
  private final int riskRate;

  public OkcoinFuturesFundsCross(@JsonProperty("account_rights") BigDecimal accountRights, @JsonProperty("keep_deposit") BigDecimal keepDeposits, @JsonProperty("profit_real") BigDecimal profitReal,
      @JsonProperty("profit_unreal") BigDecimal profitUnreal, @JsonProperty("risk_rate") int riskRate) {
  
    this.accountRights = accountRights;
    this.keepDeposits = keepDeposits;
    this.profitReal = profitReal;
    this.profitUnreal = profitUnreal;
    this.riskRate = riskRate;

  }
  
  public BigDecimal getAccountRights() {
  
    return accountRights;
  }

  
  public BigDecimal getKeepDeposits() {
  
    return keepDeposits;
  }

  
  public BigDecimal getProfitReal() {
  
    return profitReal;
  }

  
  public BigDecimal getProfitUnreal() {
  
    return profitUnreal;
  }

  
  public int getRiskRate() {
  
    return riskRate;
  }
}
