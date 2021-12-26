package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GateioFeeInfo {

  private final int no;
  private final String symbol;
  private final String name;
  private final String nameCn;
  private final String feeUsdt;
  private final String feeBtc;
  private final String feeEth;
  private final String deposit;
  private final String withdrawPercent;
  private final String withdrawFix;
  private final String withdrawDayLimit;
  private final BigDecimal withdrawAmountMini;
  private final BigDecimal withdrawDayLimitRemain;
  private final BigDecimal withdrawEachTimeLimit;
  private final BigDecimal withdrawFixOnChainEth;
  private final BigDecimal withdrawFixOnChainBtc;
  private final BigDecimal withdrawFixOnChainTrx;
  private final BigDecimal withdrawFixOnChainEos;

  public GateioFeeInfo(
      @JsonProperty("no") int no,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("name") String name,
      @JsonProperty("name_cn") String nameCn,
      @JsonProperty("fee_usdt") String feeUsdt,
      @JsonProperty("fee_btc") String feeBtc,
      @JsonProperty("fee_eth") String feeEth,
      @JsonProperty("deposit") String deposit,
      @JsonProperty("withdraw_percent") String withdrawPercent,
      @JsonProperty("withdraw_fix") String withdrawFix,
      @JsonProperty("withdraw_day_limit") String withdrawDayLimit,
      @JsonProperty("withdraw_amount_mini") BigDecimal withdrawAmountMini,
      @JsonProperty("withdraw_day_limit_remain") BigDecimal withdrawDayLimitRemain,
      @JsonProperty("withdraw_eachtime_limit") BigDecimal withdrawEachTimeLimit,
      @JsonProperty("withdraw_fix_on_chain_ETH") BigDecimal withdrawFixOnChainEth,
      @JsonProperty("withdraw_fix_on_chain_BTC") BigDecimal withdrawFixOnChainBtc,
      @JsonProperty("withdraw_fix_on_chain_TRX") BigDecimal withdrawFixOnChainTrx,
      @JsonProperty("withdraw_fix_on_chain_EOS") BigDecimal withdrawFixOnChainEos) {

    this.no = no;
    this.symbol = symbol;
    this.name = name;
    this.nameCn = nameCn;
    this.feeUsdt = feeUsdt;
    this.feeBtc = feeBtc;
    this.feeEth = feeEth;
    this.deposit = deposit;
    this.withdrawPercent = withdrawPercent;
    this.withdrawFix = withdrawFix;
    this.withdrawDayLimit = withdrawDayLimit;
    this.withdrawAmountMini = withdrawAmountMini;
    this.withdrawDayLimitRemain = withdrawDayLimitRemain;
    this.withdrawEachTimeLimit = withdrawEachTimeLimit;
    this.withdrawFixOnChainEth = withdrawFixOnChainEth;
    this.withdrawFixOnChainBtc = withdrawFixOnChainBtc;
    this.withdrawFixOnChainTrx = withdrawFixOnChainTrx;
    this.withdrawFixOnChainEos = withdrawFixOnChainEos;
  }

  @Override
  public String toString() {
    return "GateioFeeInfo{"
        + "no="
        + no
        + ", symbol='"
        + symbol
        + '\''
        + ", name='"
        + name
        + '\''
        + ", nameCn='"
        + nameCn
        + '\''
        + ", feeUsdt='"
        + feeUsdt
        + '\''
        + ", feeBtc='"
        + feeBtc
        + '\''
        + ", feeEth='"
        + feeEth
        + '\''
        + ", deposit='"
        + deposit
        + '\''
        + ", withdrawPercent='"
        + withdrawPercent
        + '\''
        + ", withdrawFix='"
        + withdrawFix
        + '\''
        + ", withdrawDayLimit='"
        + withdrawDayLimit
        + '\''
        + ", withdrawAmountMini="
        + withdrawAmountMini
        + ", withdrawDayLimitRemain="
        + withdrawDayLimitRemain
        + ", withdrawEachTimeLimit="
        + withdrawEachTimeLimit
        + ", withdrawFixOnChainEth="
        + withdrawFixOnChainEth
        + ", withdrawFixOnChainBtc="
        + withdrawFixOnChainBtc
        + ", withdrawFixOnChainTrx="
        + withdrawFixOnChainTrx
        + ", withdrawFixOnChainEos="
        + withdrawFixOnChainEos
        + '}';
  }

  public int getNo() {
    return no;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public String getNameCn() {
    return nameCn;
  }

  public String getFeeUsdt() {
    return feeUsdt;
  }

  public String getFeeBtc() {
    return feeBtc;
  }

  public String getFeeEth() {
    return feeEth;
  }

  public String getDeposit() {
    return deposit;
  }

  public String getWithdrawPercent() {
    return withdrawPercent;
  }

  public String getWithdrawFix() {
    return withdrawFix;
  }

  public String getWithdrawDayLimit() {
    return withdrawDayLimit;
  }

  public BigDecimal getWithdrawAmountMini() {
    return withdrawAmountMini;
  }

  public BigDecimal getWithdrawDayLimitRemain() {
    return withdrawDayLimitRemain;
  }

  public BigDecimal getWithdrawEachTimeLimit() {
    return withdrawEachTimeLimit;
  }

  public BigDecimal getWithdrawFixOnChainEth() {
    return withdrawFixOnChainEth;
  }

  public BigDecimal getWithdrawFixOnChainBtc() {
    return withdrawFixOnChainBtc;
  }

  public BigDecimal getWithdrawFixOnChainTrx() {
    return withdrawFixOnChainTrx;
  }

  public BigDecimal getWithdrawFixOnChainEos() {
    return withdrawFixOnChainEos;
  }
}
