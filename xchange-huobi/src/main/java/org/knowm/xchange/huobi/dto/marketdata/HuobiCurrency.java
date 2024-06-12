package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class HuobiCurrency {

  private final String chain;
  private final String displayName;
  private final String baseChain;
  private final String baseChainProtocol;
  private final boolean isDynamic;
  private final String depositStatus;
  private final String maxTransactFeeWithdraw;
  private final String maxWithdrawAmt;
  private final String minDepositAmt;
  private final String minTransactFeeWithdraw;
  private final String minWithdrawAmt;
  private final int numOfConfirmations;
  private final int numOfFastConfirmations;
  private final BigDecimal transactFeeWithdraw;
  private final String withdrawFeeType;
  private final int withdrawPrecision;
  private final String withdrawQuotaPerDay;
  private final String withdrawQuotaPerYear;
  private final String withdrawQuotaTotal;
  private final String withdrawStatus;

  public HuobiCurrency(
      @JsonProperty("chain") String chain,
      @JsonProperty("displayName") String displayName,
      @JsonProperty("baseChain") String baseChain,
      @JsonProperty("baseChainProtocol") String baseChainProtocol,
      @JsonProperty("isDynamic") boolean isDynamic,
      @JsonProperty("depositStatus") String depositStatus,
      @JsonProperty("maxTransactFeeWithdraw") String maxTransactFeeWithdraw,
      @JsonProperty("maxWithdrawAmt") String maxWithdrawAmt,
      @JsonProperty("minDepositAmt") String minDepositAmt,
      @JsonProperty("minTransactFeeWithdraw") String minTransactFeeWithdraw,
      @JsonProperty("minWithdrawAmt") String minWithdrawAmt,
      @JsonProperty("numOfConfirmations") int numOfConfirmations,
      @JsonProperty("numOfFastConfirmations") int numOfFastConfirmations,
      @JsonProperty("transactFeeWithdraw") BigDecimal transactFeeWithdraw,
      @JsonProperty("withdrawFeeType") String withdrawFeeType,
      @JsonProperty("withdrawPrecision") int withdrawPrecision,
      @JsonProperty("withdrawQuotaPerDay") String withdrawQuotaPerDay,
      @JsonProperty("withdrawQuotaPerYear") String withdrawQuotaPerYear,
      @JsonProperty("withdrawQuotaTotal") String withdrawQuotaTotal,
      @JsonProperty("withdrawStatus") String withdrawStatus) {
    this.chain = chain;
    this.displayName = displayName;
    this.baseChain = baseChain;
    this.baseChainProtocol = baseChainProtocol;
    this.isDynamic = isDynamic;
    this.depositStatus = depositStatus;
    this.maxTransactFeeWithdraw = maxTransactFeeWithdraw;
    this.maxWithdrawAmt = maxWithdrawAmt;
    this.minDepositAmt = minDepositAmt;
    this.minTransactFeeWithdraw = minTransactFeeWithdraw;
    this.minWithdrawAmt = minWithdrawAmt;
    this.numOfConfirmations = numOfConfirmations;
    this.numOfFastConfirmations = numOfFastConfirmations;
    this.transactFeeWithdraw = transactFeeWithdraw;
    this.withdrawFeeType = withdrawFeeType;
    this.withdrawPrecision = withdrawPrecision;
    this.withdrawQuotaPerDay = withdrawQuotaPerDay;
    this.withdrawQuotaPerYear = withdrawQuotaPerYear;
    this.withdrawQuotaTotal = withdrawQuotaTotal;
    this.withdrawStatus = withdrawStatus;
  }

  public String getChain() {
    return chain;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getBaseChain() {
    return baseChain;
  }

  public String getBaseChainProtocol() {
    return baseChainProtocol;
  }

  public boolean isDynamic() {
    return isDynamic;
  }

  public String getDepositStatus() {
    return depositStatus;
  }

  public String getMaxTransactFeeWithdraw() {
    return maxTransactFeeWithdraw;
  }

  public String getMaxWithdrawAmt() {
    return maxWithdrawAmt;
  }

  public String getMinDepositAmt() {
    return minDepositAmt;
  }

  public String getMinTransactFeeWithdraw() {
    return minTransactFeeWithdraw;
  }

  public String getMinWithdrawAmt() {
    return minWithdrawAmt;
  }

  public int getNumOfConfirmations() {
    return numOfConfirmations;
  }

  public int getNumOfFastConfirmations() {
    return numOfFastConfirmations;
  }

  public BigDecimal getTransactFeeWithdraw() {
    return transactFeeWithdraw;
  }

  public String getWithdrawFeeType() {
    return withdrawFeeType;
  }

  public int getWithdrawPrecision() {
    return withdrawPrecision;
  }

  public String getWithdrawQuotaPerDay() {
    return withdrawQuotaPerDay;
  }

  public String getWithdrawQuotaPerYear() {
    return withdrawQuotaPerYear;
  }

  public String getWithdrawQuotaTotal() {
    return withdrawQuotaTotal;
  }

  public String getWithdrawStatus() {
    return withdrawStatus;
  }

  @Override
  public String toString() {
    return "HuobiCurrency{"
        + "chain='"
        + chain
        + '\''
        + ", displayName='"
        + displayName
        + '\''
        + ", baseChain='"
        + baseChain
        + '\''
        + ", baseChainProtocol='"
        + baseChainProtocol
        + '\''
        + ", isDynamic="
        + isDynamic
        + ", depositStatus='"
        + depositStatus
        + '\''
        + ", maxTransactFeeWithdraw='"
        + maxTransactFeeWithdraw
        + '\''
        + ", maxWithdrawAmt='"
        + maxWithdrawAmt
        + '\''
        + ", minDepositAmt='"
        + minDepositAmt
        + '\''
        + ", minTransactFeeWithdraw='"
        + minTransactFeeWithdraw
        + '\''
        + ", minWithdrawAmt='"
        + minWithdrawAmt
        + '\''
        + ", numOfConfirmations="
        + numOfConfirmations
        + ", numOfFastConfirmations="
        + numOfFastConfirmations
        + ", transactFeeWithdraw='"
        + transactFeeWithdraw
        + '\''
        + ", withdrawFeeType='"
        + withdrawFeeType
        + '\''
        + ", withdrawPrecision="
        + withdrawPrecision
        + ", withdrawQuotaPerDay='"
        + withdrawQuotaPerDay
        + '\''
        + ", withdrawQuotaPerYear='"
        + withdrawQuotaPerYear
        + '\''
        + ", withdrawQuotaTotal='"
        + withdrawQuotaTotal
        + '\''
        + ", withdrawStatus='"
        + withdrawStatus
        + '\''
        + '}';
  }
}
