package org.knowm.xchange.bl3p.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.knowm.xchange.bl3p.dto.Bl3pAmountObj;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pOpenOrders extends Bl3pResult<Bl3pOpenOrders.Bl3pOpenOrdersData> {

  public static class Bl3pOpenOrdersData {
    @JsonProperty("orders")
    private Bl3pOpenOrder[] orders;

    public Bl3pOpenOrder[] getOrders() {
      return orders;
    }
  }

  public static class Bl3pOpenOrder {
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

    @JsonProperty("status")
    private String status;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("amount")
    private Bl3pAmountObj amount;

    @JsonProperty("amount_funds_executed")
    private Bl3pAmountObj amountFundsExecuted;

    @JsonProperty("amount_executed")
    private Bl3pAmountObj amountExecuted;

    @JsonProperty("price")
    private Bl3pAmountObj price;

    @JsonProperty("amount_funds")
    private Bl3pAmountObj amountFunds;

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

    public Bl3pAmountObj getAmountFundsExecuted() {
      return amountFundsExecuted;
    }

    public Bl3pAmountObj getAmountExecuted() {
      return amountExecuted;
    }

    public Bl3pAmountObj getPrice() {
      return price;
    }

    public Bl3pAmountObj getAmountFunds() {
      return amountFunds;
    }
  }
}
