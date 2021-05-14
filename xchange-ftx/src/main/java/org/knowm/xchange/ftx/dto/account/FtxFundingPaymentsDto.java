package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxFundingPaymentsDto {

  @JsonProperty("future")
  private final String future;

  @JsonProperty("id")
  private final String id;

  @JsonProperty("payment")
  private final BigDecimal payment;

  @JsonProperty("time")
  private final Date time;

  @JsonProperty("rate")
  private final BigDecimal rate;

  public FtxFundingPaymentsDto(
      @JsonProperty("future") String future,
      @JsonProperty("id") String id,
      @JsonProperty("payment") BigDecimal payment,
      @JsonProperty("time") Date time,
      @JsonProperty("rate") BigDecimal rate) {
    this.future = future;
    this.id = id;
    this.payment = payment;
    this.time = time;
    this.rate = rate;
  }

  public String getFuture() {
    return future;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getPayment() {
    return payment;
  }

  public Date getTime() {
    return time;
  }

  public BigDecimal getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return "FtxFundingPaymentsDto{"
        + "future='"
        + future
        + '\''
        + ", id='"
        + id
        + '\''
        + ", payment="
        + payment
        + ", time="
        + time
        + ", rate="
        + rate
        + '}';
  }
}
