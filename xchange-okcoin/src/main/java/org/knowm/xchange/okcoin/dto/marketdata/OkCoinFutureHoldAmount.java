package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinFutureHoldAmount {
  private final BigDecimal amount;
  private final String contractName;

  public OkCoinFutureHoldAmount(
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("contract_name") String contractName) {
    this.amount = amount;
    this.contractName = contractName;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getContractName() {
    return contractName;
  }

  @Override
  public String toString() {
    return "OkCoinFutureHoldAmount{"
        + "amount="
        + amount
        + ", contractName='"
        + contractName
        + '\''
        + '}';
  }
}
