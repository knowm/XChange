package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinsph.dto.CoinsphResponse;

@Getter
@ToString
public class CoinsphOrder extends CoinsphResponse {

  private final String symbol;
  private final long orderId;
  private final long orderListId; // -1 if not part of an OCO
  private final String clientOrderId;
  private final BigDecimal price;
  private final BigDecimal origQty; // "quantity" in POST request
  private final BigDecimal executedQty;
  private final BigDecimal cummulativeQuoteQty; // "total" in API response for filled MARKET order
  private final String status; // e.g., NEW, FILLED, CANCELED
  private final String timeInForce;
  private final String type; // e.g., LIMIT, MARKET
  private final String side; // e.g., BUY, SELL
  private final BigDecimal stopPrice;
  // private final BigDecimal icebergQty; // Not in Coins.ph docs
  private final long time; // "createTime" in API response
  private final long updateTime;
  private final boolean isWorking; // true if active order, not in Coins.ph but common
  private final BigDecimal origQuoteOrderQty; // "quoteOrderQty" in POST request

  public CoinsphOrder(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("orderListId") long orderListId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal origQty, // Matches "quantity" in response
      @JsonProperty("executedQuantity")
          BigDecimal executedQty, // Matches "executedQuantity" in response
      @JsonProperty("total") BigDecimal cummulativeQuoteQty, // Matches "total" in response
      @JsonProperty("status") String status,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("type") String type,
      @JsonProperty("side") String side,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      // @JsonProperty("icebergQty") BigDecimal icebergQty,
      @JsonProperty("createTime") long time, // Matches "createTime" in response
      @JsonProperty("updateTime") long updateTime,
      @JsonProperty("isWorking") Boolean isWorking, // May not be present, handle null
      @JsonProperty("quoteOrderQty")
          BigDecimal origQuoteOrderQty // Matches "quoteOrderQty" if present
      ) {
    this.symbol = symbol;
    this.orderId = orderId;
    this.orderListId = orderListId;
    this.clientOrderId = clientOrderId;
    this.price = price;
    this.origQty = origQty;
    this.executedQty = executedQty;
    this.cummulativeQuoteQty = cummulativeQuoteQty;
    this.status = status;
    this.timeInForce = timeInForce;
    this.type = type;
    this.side = side;
    this.stopPrice = stopPrice;
    // this.icebergQty = icebergQty;
    this.time = time;
    this.updateTime = updateTime;
    this.isWorking = isWorking != null ? isWorking : determineIsWorking(status);
    this.origQuoteOrderQty = origQuoteOrderQty;
  }

  private boolean determineIsWorking(String status) {
    if (status == null) return false;
    switch (status.toUpperCase()) {
      case "NEW":
      case "PARTIALLY_FILLED":
        // Add other active statuses if any
        return true;
      default:
        return false;
    }
  }
}
