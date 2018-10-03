package org.knowm.xchange.bitbay.v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class BitbayBalances extends BitbayBaseResponse {

  private List<BitbayBalance> balances;

  public BitbayBalances(
      @JsonProperty("status") String status,
      @JsonProperty("errors") List<String> errors,
      @JsonProperty("balances") List<BitbayBalance> balances) {
    super(status, errors);
    this.balances = balances;
  }

  public List<BitbayBalance> getBalances() {
    return balances;
  }

  @Override
  public String toString() {
    return "BitbayBalances{" + "balances=" + balances + '}';
  }

  public static class BitbayBalance {

    @JsonProperty("id")
    private String id;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("availableFunds")
    private BigDecimal availableFunds;

    @JsonProperty("totalFunds")
    private BigDecimal totalFunds;

    @JsonProperty("lockedFunds")
    private BigDecimal lockedFunds;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balanceEngine")
    private String balanceEngine;

    public String getId() {
      return id;
    }

    public String getUserId() {
      return userId;
    }

    public BigDecimal getAvailableFunds() {
      return availableFunds;
    }

    public BigDecimal getTotalFunds() {
      return totalFunds;
    }

    public BigDecimal getLockedFunds() {
      return lockedFunds;
    }

    public String getCurrency() {
      return currency;
    }

    public String getType() {
      return type;
    }

    public String getName() {
      return name;
    }

    public String getBalanceEngine() {
      return balanceEngine;
    }

    @Override
    public String toString() {
      return "BitbayBalance{"
          + "id='"
          + id
          + '\''
          + ", userId='"
          + userId
          + '\''
          + ", availableFunds="
          + availableFunds
          + ", totalFunds="
          + totalFunds
          + ", lockedFunds="
          + lockedFunds
          + ", currency='"
          + currency
          + '\''
          + ", type='"
          + type
          + '\''
          + ", name='"
          + name
          + '\''
          + ", balanceEngine='"
          + balanceEngine
          + '\''
          + '}';
    }
  }
}
