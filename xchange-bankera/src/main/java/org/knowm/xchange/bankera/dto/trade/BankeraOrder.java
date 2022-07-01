package org.knowm.xchange.bankera.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class BankeraOrder {

  private final long id;
  private final String market;
  private final String price;
  private final String side;
  private final String type;
  private final String status;
  private final String amount;
  private final String remainingAmount;
  private final String executedAmount;
  private final String clientOrderId;
  private final String createdAt;
  private final String updatedAt;
  private final String cancelledAt;
  private final List<BankeraTransaction> transactions;

  /**
   * @param id id of order
   * @param market order market name
   * @param price order price
   * @param side order side (buy/sell)
   * @param type order type (limit/market)
   * @param status order status
   * @param amount order amount
   * @param clientOrderId custom or generated client order id
   */
  public BankeraOrder(
      @JsonProperty("id") long id,
      @JsonProperty("market") String market,
      @JsonProperty("price") String price,
      @JsonProperty("side") String side,
      @JsonProperty("type") String type,
      @JsonProperty("status") String status,
      @JsonProperty("amount") String amount,
      @JsonProperty("executed_amount") String executedAmount,
      @JsonProperty("remaining_amount") String remainingAmount,
      @JsonProperty("client_order_id") String clientOrderId,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("updated_at") String updatedAt,
      @JsonProperty("cancelled_at") String cancelledAt,
      @JsonProperty("transactions") List<BankeraTransaction> transactions) {

    this.id = id;
    this.market = market;
    this.price = price;
    this.side = side;
    this.type = type;
    this.status = status;
    this.amount = amount;
    this.clientOrderId = clientOrderId;
    this.executedAmount = executedAmount;
    this.remainingAmount = remainingAmount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.cancelledAt = cancelledAt;
    this.transactions = transactions;
  }

  public long getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public String getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public String getAmount() {
    return amount;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public String getRemainingAmount() {
    return remainingAmount;
  }

  public String getExecutedAmount() {
    return executedAmount;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public String getCancelledAt() {
    return cancelledAt;
  }

  public List<BankeraTransaction> getTransactions() {
    return transactions;
  }

  public BigDecimal getTotalFee() {
    Function<BankeraTransaction, BigDecimal> totalMapper = tx -> new BigDecimal(tx.getFee());
    return transactions.size() > 0
        ? transactions.stream().map(totalMapper).reduce(BigDecimal.ZERO, BigDecimal::add)
        : BigDecimal.ZERO;
  }
}
