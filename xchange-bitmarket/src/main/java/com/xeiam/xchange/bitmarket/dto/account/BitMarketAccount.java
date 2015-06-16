package com.xeiam.xchange.bitmarket.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 24/04/15
 * Time: 12:28
 */
public class BitMarketAccount {

  /*
  {"success":true,
  "data":
    {"balances":
      {"available":
          {"PLN":0.00000000,
          "EUR":0.00000000,
          "BTC":0.00000000,
          "LTC":0.00000000,
          "DOGE":0.00000000,
          "PPC":0.00000000,
          "LiteMineX":0.00000000},
      "blocked":
          {"PLN":0.00000000,
          "BTC":0.00000000,
          "EUR":0.00000000,
          "LTC":0.00000000,
          "LiteMineX":0.00000000}},
      "account":
        {"turnover":0.0000,"commissionMaker":0.1500,"commissionTaker":0.4500}}
   */

  private final AccountInfo account;
  private final AccountBalance balances;

  public BitMarketAccount(@JsonProperty("balances") AccountBalance balances,
                          @JsonProperty("account") AccountInfo account) {
    this.balances = balances;
    this.account = account;
  }

  public AccountInfo getAccount() {
    return account;
  }

  public AccountBalance getBalances() {
    return balances;
  }

  public static class AccountBalance {
    private Map<String, BigDecimal> available;
    private Map<String, BigDecimal> blocked;

    @JsonCreator
    public AccountBalance(@JsonProperty("available") Map<String, BigDecimal> available,
                          @JsonProperty("blocked") Map<String, BigDecimal> blocked) {
      this.available = available;
      this.blocked = blocked;
    }

    public Map<String, BigDecimal> getAvailable() {
      return available;
    }

    public Map<String, BigDecimal> getBlocked() {
      return blocked;
    }
  }

  public static class AccountInfo {
    private BigDecimal turnover;
    private BigDecimal commissionMaker;
    private BigDecimal commissionTaker;


    public AccountInfo(@JsonProperty("turnover") BigDecimal turnover,
                       @JsonProperty("commissionMaker") BigDecimal commissionMaker,
                       @JsonProperty("commissionTaker") BigDecimal commissionTaker) {
      this.turnover = turnover;
      this.commissionMaker = commissionMaker;
      this.commissionTaker = commissionTaker;
    }

    public BigDecimal getTurnover() {
      return turnover;
    }

    public BigDecimal getCommissionMaker() {
      return commissionMaker;
    }

    public BigDecimal getCommissionTaker() {
      return commissionTaker;
    }
  }
}

