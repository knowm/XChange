package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BittrexOrder extends BittrexOrderBase {

  private final String accountId;
  private final BigDecimal reserved;
  private final BigDecimal reserveRemaining;
  private final BigDecimal commissionReserved;
  private final BigDecimal commissionReserveRemaining;
  private final Boolean isOpen;
  private final String sentinel;

  public BittrexOrder(
      @JsonProperty("AccountId") String accountId,
      @JsonProperty("OrderUuid") String orderUuid,
      @JsonProperty("Exchange") String exchange,
      @JsonProperty("Type") String type,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("QuantityRemaining") BigDecimal quantityRemaining,
      @JsonProperty("Limit") BigDecimal limit,
      @JsonProperty("Reserved") BigDecimal reserved,
      @JsonProperty("ReserveRemaining") BigDecimal reserveRemaining,
      @JsonProperty("CommissionReserved") BigDecimal commissionReserved,
      @JsonProperty("CommissionReserveRemaining") BigDecimal commissionReserveRemaining,
      @JsonProperty("CommissionPaid") BigDecimal commissionPaid,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PricePerUnit") BigDecimal pricePerUnit,
      @JsonProperty("Opened") Date opened,
      @JsonProperty("Closed") Date closed,
      @JsonProperty("IsOpen") Boolean isOpen,
      @JsonProperty("Sentinel") String sentinel,
      @JsonProperty("CancelInitiated") Boolean cancelInitiated,
      @JsonProperty("ImmediateOrCancel") Boolean immediateOrCancel,
      @JsonProperty("IsConditional") Boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") String conditionTarget) {
    super(
        orderUuid,
        exchange,
        type,
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
    this.accountId = accountId;
    this.reserved = reserved;
    this.reserveRemaining = reserveRemaining;
    this.commissionReserved = commissionReserved;
    this.commissionReserveRemaining = commissionReserveRemaining;
    this.isOpen = isOpen;
    this.sentinel = sentinel;
  }

  public String getAccountId() {
    return accountId;
  }

  public BigDecimal getReserved() {
    return reserved;
  }

  public BigDecimal getReserveRemaining() {
    return reserveRemaining;
  }

  public BigDecimal getCommissionReserved() {
    return commissionReserved;
  }

  public BigDecimal getCommissionReserveRemaining() {
    return commissionReserveRemaining;
  }

  public Boolean getOpen() {
    return isOpen;
  }

  public String getSentinel() {
    return sentinel;
  }

  @Override
  protected String additionalToString() {
    return "accountId="
        + accountId
        + ", reserved="
        + reserved
        + ", reserveRemaining="
        + reserveRemaining
        + ", commissionReserved="
        + commissionReserved
        + ", commissionReserveRemaining="
        + commissionReserveRemaining
        + ", isOpen="
        + isOpen
        + ", sentinel="
        + sentinel;
  }
}
