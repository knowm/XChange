package org.knowm.xchange.bitrue.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public final class BitrueDribblets {
  private final Long operateTime;
  private final BigDecimal totalTransferedAmount;
  private final BigDecimal totalServiceChargeAmount;
  private final String transId;
  private final List<BitrueDribbletDetails> binanceDribbletDetails;

  public BitrueDribblets(
      @JsonProperty("operateTime") Long operateTime,
      @JsonProperty("totalTransferedAmount") BigDecimal totalTransferedAmount,
      @JsonProperty("totalServiceChargeAmount") BigDecimal totalServiceChargeAmount,
      @JsonProperty("transId") String transId,
      @JsonProperty("userAssetDribbletDetails")
          List<BitrueDribbletDetails> binanceDribbletDetails) {
    this.operateTime = operateTime;
    this.totalTransferedAmount = totalTransferedAmount;
    this.totalServiceChargeAmount = totalServiceChargeAmount;
    this.transId = transId;
    this.binanceDribbletDetails = binanceDribbletDetails;
  }

  public Long getOperateTime() {
    return operateTime;
  }

  public BigDecimal getTotalTransferedAmount() {
    return totalTransferedAmount;
  }

  public BigDecimal getTotalServiceChargeAmount() {
    return totalServiceChargeAmount;
  }

  public String getTransId() {
    return transId;
  }

  public List<BitrueDribbletDetails> getBinanceDribbletDetails() {
    return binanceDribbletDetails;
  }
}
