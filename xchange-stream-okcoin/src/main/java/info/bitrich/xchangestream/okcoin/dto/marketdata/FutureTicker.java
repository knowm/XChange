package info.bitrich.xchangestream.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FutureTicker {
  private Long contractId; // 合约ID
  private Integer unitAmount; // 合约价值
  private BigDecimal holdAmount; // 当前持仓量

  private BigDecimal limitHigh; // 最高买入限制价格
  private BigDecimal limitLow; // 最低卖出限制价格

  private BigDecimal buy; // 买一价格
  private BigDecimal sell; // 卖一价格
  private BigDecimal last; // 最新成交价

  private BigDecimal high; // 24小时最高价格
  private BigDecimal low; // 24小时最低价格
  private BigDecimal vol; // 24小时成交量

  public FutureTicker(
      @JsonProperty("contractId") final Long contractId,
      @JsonProperty("unitAmount") final Integer unitAmount,
      @JsonProperty("hold_amount") final BigDecimal holdAmount,
      @JsonProperty("limitHigh") final BigDecimal limitHigh,
      @JsonProperty("limitLow") final BigDecimal limitLow,
      @JsonProperty("buy") final BigDecimal buy,
      @JsonProperty("sell") final BigDecimal sell,
      @JsonProperty("last") final BigDecimal last,
      @JsonProperty("high") final BigDecimal high,
      @JsonProperty("low") final BigDecimal low,
      @JsonProperty("vol") final BigDecimal vol) {

    this.contractId = contractId;
    this.unitAmount = unitAmount;
    this.holdAmount = holdAmount;
    this.limitHigh = limitHigh;
    this.limitLow = limitLow;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.high = high;
    this.low = low;
    this.vol = vol;
  }

  public Long getContractId() {
    return contractId;
  }

  public Integer getUnitAmount() {
    return unitAmount;
  }

  public BigDecimal getHoldAmount() {
    return holdAmount;
  }

  public BigDecimal getLimitHigh() {
    return limitHigh;
  }

  public BigDecimal getLimitLow() {
    return limitLow;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVol() {
    return vol;
  }
}
