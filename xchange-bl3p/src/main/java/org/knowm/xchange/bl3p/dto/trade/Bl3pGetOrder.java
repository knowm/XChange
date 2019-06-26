package org.knowm.xchange.bl3p.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.knowm.xchange.bl3p.dto.Bl3pAmountObj;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pGetOrder extends Bl3pResult<Bl3pGetOrder.Bl3pGetOrderData> {

  public static class Bl3pGetOrderData {
    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("label")
    private String label;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("item")
    private String item;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private Bl3pAmountObj amount;

    @JsonProperty("price")
    private Bl3pAmountObj price;

    @JsonProperty("status")
    private String status;

    @JsonProperty("date")
    private Date timestamp;

    @JsonProperty("total_amount")
    private Bl3pAmountObj totalAmount;

    @JsonProperty("total_spent")
    private Bl3pAmountObj totalSpent;

    @JsonProperty("total_fee")
    private Bl3pAmountObj totalFee;

    @JsonProperty("avg_cost")
    private Bl3pAmountObj avgCost;

    @JsonProperty("trades")
    private Bl3pGetOrderTrade[] trades;

    public int getOrderId() {
      return orderId;
    }

    public String getLabel() {
      return label;
    }

    public String getCurrency() {
      return currency;
    }

    public String getItem() {
      return item;
    }

    public String getType() {
      return type;
    }

    public String getStatus() {
      return status;
    }

    public Date getTimestamp() {
      return timestamp;
    }

    public Bl3pAmountObj getAmount() {
      return amount;
    }

    public Bl3pAmountObj getPrice() {
      return price;
    }

    public Bl3pAmountObj getTotalAmount() {
      return totalAmount;
    }

    public Bl3pAmountObj getTotalSpent() {
      return totalSpent;
    }

    public Bl3pAmountObj getTotalFee() {
      return totalFee;
    }

    public Bl3pAmountObj getAvgCost() {
      return avgCost;
    }

    public Bl3pGetOrderTrade[] getTrades() {
      return trades;
    }
  }

  public static class Bl3pGetOrderTrade {
    @JsonProperty("amount")
    private Bl3pAmountObj amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("item")
    private String item;

    @JsonProperty("price")
    private Bl3pAmountObj price;

    @JsonProperty("trade_id")
    private int tradeId;

    public Bl3pAmountObj getAmount() {
      return amount;
    }

    public String getCurrency() {
      return currency;
    }

    public Date getDate() {
      return date;
    }

    public String getItem() {
      return item;
    }

    public Bl3pAmountObj getPrice() {
      return price;
    }

    public int getTradeId() {
      return tradeId;
    }
  }
}
