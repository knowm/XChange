package org.knowm.xchange.therock.dto.trade;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import org.knowm.xchange.therock.TheRock;

/**
 * @author Matija Mazi
 * @author Pnk
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockOrder {

  private Long id;
  private TheRock.Pair fundId;
  private Side side;
  private Type type;
  private String status;
  private BigDecimal amount;
  private BigDecimal amountUnfilled;
  private BigDecimal price;
  private String conditionalType;
  private BigDecimal conditionalPrice;
  private String date;
  private String closeOn;
  private boolean dark;
  private BigDecimal leverage;
  private long positionId;

  protected TheRockOrder() {}

  public TheRockOrder(
      TheRock.Pair fundId, Side side, Type type, BigDecimal amount, BigDecimal price) {
    this.fundId = fundId;
    this.side = side;
    this.type = type;
    this.amount = amount;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public TheRock.Pair getFundId() {
    return fundId;
  }

  public Side getSide() {
    return side;
  }

  public Type getType() {
    return type;
  }

  public String getStatus() {
    return status;
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

  public String getConditionalType() {
    return conditionalType;
  }

  public BigDecimal getConditionalPrice() {
    return conditionalPrice;
  }

  public String getDate() {
    return date;
  }

  public String getCloseOn() {
    return closeOn;
  }

  public boolean isDark() {
    return dark;
  }

  public BigDecimal getLeverage() {
    return leverage;
  }

  public long getPositionId() {
    return positionId;
  }

  @Override
  public String toString() {
    return String.format(
        "TheRockOrder{id=%d, side=%s, type=%s, amount=%s, amountUnfilled=%s, price=%s, fundId=%s, status='%s'}",
        id, side, type, amount, amountUnfilled, price, fundId, status);
  }

  public enum Side {
    buy,
    sell
  }

  public enum Type {
    market,
    limit
  }
}
