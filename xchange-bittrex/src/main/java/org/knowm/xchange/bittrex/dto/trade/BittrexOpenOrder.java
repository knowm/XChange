package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BittrexOpenOrder extends BittrexOrderBase {

  private final String uuid;

  public BittrexOpenOrder(
      @JsonProperty("Uuid") String uuid,
      @JsonProperty("OrderUuid") String orderUuid,
      @JsonProperty("Exchange") String exchange,
      @JsonProperty("OrderType") String orderType,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("QuantityRemaining") BigDecimal quantityRemaining,
      @JsonProperty("Limit") BigDecimal limit,
      @JsonProperty("CommissionPaid") BigDecimal commissionPaid,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PricePerUnit") BigDecimal pricePerUnit,
      @JsonProperty("Opened") Date opened,
      @JsonProperty("Closed") Date closed,
      @JsonProperty("CancelInitiated") Boolean cancelInitiated,
      @JsonProperty("ImmediateOrCancel") Boolean immediateOrCancel,
      @JsonProperty("IsConditional") Boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") Object conditionTarget) {
    super(
        orderUuid,
        exchange,
        orderType,
        quantity,
        quantityRemaining,
        limit,
        commissionPaid,
        price,
        pricePerUnit,
        opened,
        closed,
        cancelInitiated,
        immediateOrCancel,
        isConditional,
        condition,
        conditionTarget);
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }

  @Override
  protected String additionalToString() {
    return "uuid=" + getUuid();
  }
}
