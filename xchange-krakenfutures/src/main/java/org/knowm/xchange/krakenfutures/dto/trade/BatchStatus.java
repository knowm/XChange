package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class BatchStatus {
  /** Always send */
  public final Date receivedTime;

  public final OrderStatus status;
  public final String orderId;
  /**
   * For placement only. The arbitrary string provided client-side when the order was sent for the
   * purpose of mapping order sending instructions to the API’s response
   */
  public final String orderTag;

  public final String cliOrdId;

  public BatchStatus(
      @JsonProperty("receivedTime") Date receivedTime,
      @JsonProperty("status") OrderStatus status,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_tag") String orderTag,
      @JsonProperty("cliOrdId") String cliOrdId) {
    this.receivedTime = receivedTime;
    this.status = status;
    this.orderId = orderId;
    this.orderTag = orderTag;
    this.cliOrdId = cliOrdId;
  }

  public enum OrderStatus {
    // The status of the order placement
    placed, // the order was placed successfully
    attempted, // the post-order will be attempted. If it is successful it will be shown in open
    // orders
    invalidOrderType, // the order was not placed because orderType is invalid
    invalidSide, // the order was not placed because side is invalid
    invalidSize, // the order was not placed because size is invalid
    invalidPrice, // the order was not placed because limitPrice and/or stopPrice are invalid
    insufficientAvailableFunds, // the order was not placed because available funds are insufficient
    selfFill, // the order was not placed because it would be filled against an existing order
    // belonging to the same account
    tooManySmallOrders, // the order was not placed because the number of small open orders would
    // exceed the permissible limit
    marketSuspended, // the order was not placed because the market is suspended
    marketInactive, // the order was not placed because the market is inactive
    clientOrderIdAlreadyExist, // the specified client id already exist
    clientOrderIdTooLong, // the client id is longer than the permissible limit
    maxPositionViolation, // not documented
    outsidePriceCollar, // the limit order crosses the spread but is an order of magnitude away from
    // the mark price - fat finger control
    postWouldExecute, // the post-only order would be filled upon placement, thus is cancelled
    iocWouldNotExecute, // the immediate-or-cancel order would not call

    // The status of order cancellation
    cancelled, // the order was found untouched and the entire size was cancelled successfully
    filled, // the order was found completely filled and could not be cancelled
    notFound, // the order was not found, either because it had already been cancelled or it never
    // existed
    requiredArgumentMissing // the request does not contain order_id and/or cliOrdId parameters
  }

  @Override
  public String toString() {
    return "BatchStatus ["
        + (receivedTime != null ? "receivedTime=" + receivedTime + ", " : "")
        + (status != null ? "status=" + status + ", " : "")
        + (orderId != null ? "orderId=" + orderId + ", " : "")
        + (orderTag != null ? "orderTag=" + orderTag + ", " : "")
        + (cliOrdId != null ? "cliOrdId=" + cliOrdId : "")
        + "]";
  }
}
