package org.knowm.xchange.coinsetter.dto.order.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An order.
 */
public class CoinsetterOrder {

  private final UUID uuid;
  private final UUID customerUuid;
  private final UUID accountUuid;
  private final String orderNumber;
  private final String stage;
  private final String orderType;
  private final String side;
  private final String symbol;
  private final BigDecimal requestedQuantity;
  private final BigDecimal filledQuantity;
  private final BigDecimal openQuantity;
  private final BigDecimal requestedPrice;
  private final BigDecimal costBasis;
  private final BigDecimal commission;
  private final int routingMethod;
  private final Date createDate;

  /**
   * @param uuid Order UUID
   * @param customerUuid Customer UUID
   * @param accountUuid Account UUID
   * @param orderNumber Order #
   * @param stage Stage (i.e. "NEW", "PENDING", "OPEN", "PARTIAL_FILL", "EXPIRED", "CANCELLED", "REJECTED", "CLOSED", "EXT_ROUTED")
   * @param orderType "MARKET" or "LIMIT"
   * @param side "BUY" or "SELL"
   * @param symbol Ticker symbol (i.e. "BTCUSD")
   * @param requestedQuantity Requested quantity
   * @param filledQuantity Quantity filled
   * @param openQuantity Quantity open
   * @param requestedPrice Requested price
   * @param costBasis Cost basis (filledQuantity * average price) not including commission
   * @param commission Commission paid
   * @param routingMethod How your order has been routed (1 for SMART routing, 2 for COINSETTER routing)
   * @param createDate Epoch Date
   */
  public CoinsetterOrder(@JsonProperty("uuid") UUID uuid, @JsonProperty("customerUuid") UUID customerUuid,
      @JsonProperty("accountUuid") UUID accountUuid, @JsonProperty("orderNumber") String orderNumber, @JsonProperty("stage") String stage,
      @JsonProperty("orderType") String orderType, @JsonProperty("side") String side, @JsonProperty("symbol") String symbol,
      @JsonProperty("requestedQuantity") BigDecimal requestedQuantity, @JsonProperty("filledQuantity") BigDecimal filledQuantity,
      @JsonProperty("openQuantity") BigDecimal openQuantity, @JsonProperty("requestedPrice") BigDecimal requestedPrice,
      @JsonProperty("costBasis") BigDecimal costBasis, @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("routingMethod") int routingMethod, @JsonProperty("createDate") Date createDate) {

    this.uuid = uuid;
    this.customerUuid = customerUuid;
    this.accountUuid = accountUuid;
    this.orderNumber = orderNumber;
    this.stage = stage;
    this.orderType = orderType;
    this.side = side;
    this.symbol = symbol;
    this.requestedQuantity = requestedQuantity;
    this.filledQuantity = filledQuantity;
    this.openQuantity = openQuantity;
    this.requestedPrice = requestedPrice;
    this.costBasis = costBasis;
    this.commission = commission;
    this.routingMethod = routingMethod;
    this.createDate = createDate;
  }

  public UUID getUuid() {

    return uuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public UUID getAccountUuid() {

    return accountUuid;
  }

  public String getOrderNumber() {

    return orderNumber;
  }

  public String getStage() {

    return stage;
  }

  public String getOrderType() {

    return orderType;
  }

  public String getSide() {

    return side;
  }

  public String getSymbol() {

    return symbol;
  }

  public BigDecimal getRequestedQuantity() {

    return requestedQuantity;
  }

  public BigDecimal getFilledQuantity() {

    return filledQuantity;
  }

  public BigDecimal getOpenQuantity() {

    return openQuantity;
  }

  public BigDecimal getRequestedPrice() {

    return requestedPrice;
  }

  public BigDecimal getCostBasis() {

    return costBasis;
  }

  public BigDecimal getCommission() {

    return commission;
  }

  public int getRoutingMethod() {

    return routingMethod;
  }

  public Date getCreateDate() {

    return createDate;
  }

}
