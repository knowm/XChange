package org.knowm.xchange.kucoin.dto.trading;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import javax.annotation.Generated;
import org.knowm.xchange.dto.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "coinType",
  "dealValueTotal",
  "dealPriceAverage",
  "feeTotal",
  "userOid",
  "dealAmount",
  "coinTypePair",
  "orderPrice",
  "type",
  "orderOid",
  "pendingAmount",
  "isActive"
})
public class KucoinOrderDetail {

  /** Order coin type */
  private String coinType;

  /** Order deal value total */
  private BigDecimal dealValueTotal;

  /** Order average price */
  private BigDecimal dealPriceAverage;

  /** Order total fee */
  private BigDecimal feeTotal;

  /** Order user defined order id */
  private String userOid;

  /** Order deal amount */
  private BigDecimal dealAmount;

  /** Order coin type pair */
  private String coinTypePair;

  /** Order price */
  private BigDecimal orderPrice;

  /** Order type i.e. bid or ask */
  private Order.OrderType type;

  /** Order unique id */
  private String orderOid;

  /** Order pending amount */
  private BigDecimal pendingAmount;

  /** Is order in orderbook */
  private Boolean isActive;

  /**
   * INFO: Maybe we need to add dealOrders list here from request, but we don't need it in logic.
   */
  public String getCoinType() {
    return coinType;
  }

  public BigDecimal getDealValueTotal() {
    return dealValueTotal;
  }

  public BigDecimal getDealPriceAverage() {
    return dealPriceAverage;
  }

  public BigDecimal getFeeTotal() {
    return feeTotal;
  }

  public String getUserOid() {
    return userOid;
  }

  public BigDecimal getDealAmount() {
    return dealAmount;
  }

  public String getCoinTypePair() {
    return coinTypePair;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public Order.OrderType getType() {
    return type;
  }

  public String getOrderOid() {
    return orderOid;
  }

  public BigDecimal getPendingAmount() {
    return pendingAmount;
  }

  public Boolean getIsActive() {
    return isActive;
  }
}
