package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class BinanceDribbletDetails {
  private final String transId;
  private final BigDecimal serviceChargeAmount;
  private final BigDecimal amount;
  private final Long operateTime;
  private final BigDecimal transferedAmount;
  private final String fromAsset;

  public BinanceDribbletDetails(
      @JsonProperty("transId") String transId,
      @JsonProperty("serviceChargeAmount") BigDecimal serviceChargeAmount,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("operateTime") Long operateTime,
      @JsonProperty("transferedAmount") BigDecimal transferedAmount,
      @JsonProperty("fromAsset") String fromAsset) {
    this.transId = transId;
    this.serviceChargeAmount = serviceChargeAmount;
    this.amount = amount;
    this.operateTime = operateTime;
    this.transferedAmount = transferedAmount;
    this.fromAsset = fromAsset;
  }

  public String getTransId() {
    return transId;
  }

  public BigDecimal getServiceChargeAmount() {
    return serviceChargeAmount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Long getOperateTime() {
    return operateTime;
  }

  public BigDecimal getTransferedAmount() {
    return transferedAmount;
  }

  public String getFromAsset() {
    return fromAsset;
  }
}
