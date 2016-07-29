package org.knowm.xchange.coinsetter.dto.order.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Request for placing order.
 */
@JsonInclude(Include.NON_NULL)
public class CoinsetterOrderRequest {

  private final UUID customerUuid;
  private final UUID accountUuid;
  private final String symbol;
  private final String side;
  private final String orderType;
  private final BigDecimal requestedQuantity;
  private final int routingMethod;
  private final BigDecimal requestedPrice;

  private String clientOrderId;
  private String quantityDenomination;

  /**
   * @param customerUuid Customer UUID
   * @param accountUuid Account UUID
   * @param symbol Symbol (We Currently only support "BTCUSD")
   * @param side Side ("BUY" or "SELL")
   * @param orderType Order type ("MARKET" or "LIMIT")
   * @param requestedQuantity Requested quantity to buy/sell. (Integer with up to two decimal places)
   * @param routingMethod How your order Will be routed (1 for SMART routing, 2 for COINSETTER routing). If the order is routed to SMART you will be
   *        using the aggregate OrderBook. If you route the order to COINSETTER you will use COINSETTER orderBook only.
   * @param requestedPrice Requested price (Integer with up to two decimal places)
   */
  public CoinsetterOrderRequest(UUID customerUuid, UUID accountUuid, String symbol, String side, String orderType, BigDecimal requestedQuantity,
      int routingMethod, BigDecimal requestedPrice) {

    this.customerUuid = customerUuid;
    this.accountUuid = accountUuid;
    this.symbol = symbol;
    this.side = side;
    this.orderType = orderType;
    this.requestedQuantity = requestedQuantity;
    this.routingMethod = routingMethod;
    this.requestedPrice = requestedPrice;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  /**
   * @param clientOrderId Client assigned order identifier
   */
  public void setClientOrderId(String clientOrderId) {

    this.clientOrderId = clientOrderId;
  }

  public String getQuantityDenomination() {

    return quantityDenomination;
  }

  /**
   * @param quantityDenomination Quantity Denomination ("USD" or "BTC"). Only for MARKET orders. BTC is the default.
   */
  public void setQuantityDenomination(String quantityDenomination) {

    this.quantityDenomination = quantityDenomination;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public UUID getAccountUuid() {

    return accountUuid;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public String getOrderType() {

    return orderType;
  }

  public BigDecimal getRequestedQuantity() {

    return requestedQuantity;
  }

  public int getRoutingMethod() {

    return routingMethod;
  }

  public BigDecimal getRequestedPrice() {

    return requestedPrice;
  }

}
