package org.knowm.xchange.therock.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.knowm.xchange.therock.TheRock;

/**
 * @author Matija Mazi
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockOrder {

  private Long id;
  private Side side;
  private Type type;
  private BigDecimal amount;
  private BigDecimal amountUnfilled;
  private BigDecimal price;
  private TheRock.Pair fundId;
  private String status;

  protected TheRockOrder() {
  }

  public TheRockOrder(TheRock.Pair fundId, Side side, Type type, BigDecimal amount, BigDecimal price) {
    this.fundId = fundId;
    this.side = side;
    this.type = type;
    this.amount = amount;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public Side getSide() {
    return side;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getAmountUnfilled() {
    return amountUnfilled;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public TheRock.Pair getFundId() {
    return fundId;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format("TheRockOrder{id=%d, side=%s, type=%s, amount=%s, amountUnfilled=%s, price=%s, fundId=%s, status='%s'}", id, side, type,
        amount, amountUnfilled, price, fundId, status);
  }

  public enum Side {
    buy, sell
  }

  public enum Type {
    market, limit
  }
}