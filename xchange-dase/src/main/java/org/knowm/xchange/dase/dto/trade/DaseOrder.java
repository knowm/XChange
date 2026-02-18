package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Immutable DTO representing an order from DASE REST API. */
public class DaseOrder {

  private final String id;
  private final String portfolioId;
  private final String market;
  private final String type; // "limit" | "market"
  private final String side; // "buy" | "sell"

  private final String size; // string, nullable
  private final String price; // string, nullable
  private final String funds; // string, nullable

  private final String filled; // string decimal
  private final String filledCost; // string decimal
  private final String filledPrice; // string decimal

  private final String status; // "open" | "canceled" | "closed"
  private final String reason; // nullable
  private final Long createdAt; // epoch millis (may be fractional in docs, we keep long millis)
  private final Long updatedAt; // nullable

  private final String clientId; // nullable

  @JsonCreator
  public DaseOrder(
      @JsonProperty("id") String id,
      @JsonProperty("portfolio_id") String portfolioId,
      @JsonProperty("market") String market,
      @JsonProperty("type") String type,
      @JsonProperty("side") String side,
      @JsonProperty("size") String size,
      @JsonProperty("price") String price,
      @JsonProperty("funds") String funds,
      @JsonProperty("filled") String filled,
      @JsonProperty("filled_cost") String filledCost,
      @JsonProperty("filled_price") String filledPrice,
      @JsonProperty("status") String status,
      @JsonProperty("reason") String reason,
      @JsonProperty("created_at") Number createdAt,
      @JsonProperty("updated_at") Number updatedAt,
      @JsonProperty("client_id") String clientId) {
    this.id = id;
    this.portfolioId = portfolioId;
    this.market = market;
    this.type = type;
    this.side = side;
    this.size = size;
    this.price = price;
    this.funds = funds;
    this.filled = filled;
    this.filledCost = filledCost;
    this.filledPrice = filledPrice;
    this.status = status;
    this.reason = reason;
    this.createdAt = createdAt == null ? null : createdAt.longValue();
    this.updatedAt = updatedAt == null ? null : updatedAt.longValue();
    this.clientId = clientId;
  }

  public String getId() {
    return id;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public String getMarket() {
    return market;
  }

  public String getType() {
    return type;
  }

  public String getSide() {
    return side;
  }

  public String getSize() {
    return size;
  }

  public String getPrice() {
    return price;
  }

  public String getFunds() {
    return funds;
  }

  public String getFilled() {
    return filled;
  }

  public String getFilledCost() {
    return filledCost;
  }

  public String getFilledPrice() {
    return filledPrice;
  }

  public String getStatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public String getClientId() {
    return clientId;
  }
}
