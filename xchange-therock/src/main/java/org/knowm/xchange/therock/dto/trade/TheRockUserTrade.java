package org.knowm.xchange.therock.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.therock.dto.marketdata.TheRockTrade.Side;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TheRockUserTrade {

  private final long id;
  private final String fundId;
  private final BigDecimal amount;
  private final Date date;
  private final BigDecimal price;
  private final Side side;
  private final long orderId;
  private final TheRockUserTradeTransaction feeTransaction;

  public TheRockUserTrade(@JsonProperty("id") long id, @JsonProperty("fund_id") String fundId, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("price") BigDecimal price, @JsonProperty("date") Date date, @JsonProperty("side") Side tradeSide,
      @JsonProperty("order_id") long orderId, @JsonProperty("transactions") TheRockUserTradeTransaction[] transactions) {
    this.id = id;
    this.fundId = fundId;
    this.amount = amount;
    this.price = price;
    this.date = date;
    this.side = tradeSide;
    this.orderId = orderId;

    // try to find the fee transaction
    TheRockUserTradeTransaction ft = null;
    for (TheRockUserTradeTransaction t : transactions) {
      if (t.type == TransactionType.paid_commission) {
        ft = t;
        break;
      }
    }
    feeTransaction = ft;

  }

  public long getId() {
    return id;
  }

  public String getFundId() {
    return fundId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Date getDate() {
    return date;
  }

  public Side getSide() {
    return side;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getFeeAmount() {
    return feeTransaction == null ? BigDecimal.ZERO : feeTransaction.price;
  }

  public String getFeeCurrency() {
    return feeTransaction == null ? null : feeTransaction.currency;
  }

  @Override
  public String toString() {
    return "TheRockTrade [amount=" + amount + ", date=" + date + ", price=" + price + ", id=" + id + ", side=" + side + "]";
  }

  @SuppressWarnings("unused")
  private static class TheRockUserTradeTransaction {
    private final long id;
    private final Date date;
    private final TransactionType type;
    private final BigDecimal price;
    private final String currency;

    public TheRockUserTradeTransaction(@JsonProperty("id") long id, @JsonProperty("date") Date date, @JsonProperty("type") TransactionType type,
        @JsonProperty("price") BigDecimal price, @JsonProperty("currency") String currency) {
      super();
      this.id = id;
      this.date = date;
      this.type = type;
      this.price = price;
      this.currency = currency;
    }
  }

  private enum TransactionType {
    sold_currency_to_fund, released_currency_to_fund, paid_commission, bought_currency_from_fund, acquired_currency_from_fund, unknown;

    @JsonCreator
    public static TransactionType fromString(String string) {
      try {
        return TransactionType.valueOf(string);
      } catch (Throwable e) {
        return unknown;
      }
    }
  }
}
