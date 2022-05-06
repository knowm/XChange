package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxTriggerDto {

  private final Date time;
  private final String error;
  private final BigDecimal filledSize;
  private final BigDecimal orderSize;
  private final String orderId;

  @JsonCreator
  public FtxTriggerDto(
      @JsonProperty("time") Date time,
      @JsonProperty("error") String error,
      @JsonProperty("filledSize") BigDecimal filledSize,
      @JsonProperty("orderSize") BigDecimal orderSize,
      @JsonProperty("orderId") String orderId) {
    this.time = time;
    this.error = error;
    this.filledSize = filledSize;
    this.orderSize = orderSize;
    this.orderId = orderId;
  }

  public Date getTime() {
    return time;
  }

  public String getError() {
    return error;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public BigDecimal getOrderSize() {
    return orderSize;
  }

  public String getOrderId() {
    return orderId;
  }

  @Override
  public String toString() {
    return "FtxOrderDto{"
        + "time="
        + time
        + ", error="
        + error
        + ", filledSize="
        + filledSize
        + ", orderSize="
        + orderSize
        + ", orderId="
        + orderId
        + '}';
  }
}
