package com.xeiam.xchange.bitvc.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

//{"orders":[{"id":286462,"order_amount":"0.001","processed_amount":"0.0000","order_time":1411233460,"type":2,"order_price":"2700"},
//           {"id":286417,"order_amount":"0.001","processed_amount":"0.0000","order_time":1411232607,"type":2,"order_price":"2570"}]} 

public class BitVcOrder {

  private final long id;

  /**
   * 1 = buy, 2 = sell.
   */
  private final int type;
  private final BigDecimal orderPrice;
  private final BigDecimal orderAmount;
  private final BigDecimal processedAmount;

  /**
   * Average price.
   */
  private final long orderTime;

  /**
   * 0 = Unfilled,　1 = Partially filled,　2 = Finished,　3 = Cancelled.
   */

  public BitVcOrder(@JsonProperty("id") final long id, @JsonProperty("type") final int type,
      @JsonProperty("order_price") final BigDecimal orderPrice, @JsonProperty("order_amount") final BigDecimal orderAmount,
      @JsonProperty("processed_amount") final BigDecimal processedAmount, @JsonProperty("order_time") final long orderTime) {

    this.id = id;
    this.type = type;
    this.orderPrice = orderPrice;
    this.orderAmount = orderAmount;
    this.processedAmount = processedAmount;

    this.orderTime = orderTime;
  }

  public long getId() {

    return id;
  }

  public int getType() {

    return type;
  }

  public BigDecimal getOrderPrice() {

    return orderPrice;
  }

  public BigDecimal getOrderAmount() {

    return orderAmount;
  }

  public BigDecimal getProcessedAmount() {

    return processedAmount;
  }

  public long getOrderTime() {

    return orderTime;
  }

}
