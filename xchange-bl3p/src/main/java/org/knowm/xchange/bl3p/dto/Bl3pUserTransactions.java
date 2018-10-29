package org.knowm.xchange.bl3p.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Bl3pUserTransactions
    extends Bl3pResult<Bl3pUserTransactions.Bl3pUserTransactionsData> {

  public static class Bl3pUserTransactionsData {

    @JsonProperty("page")
    public int page;

    @JsonProperty("records")
    public int records;

    @JsonProperty("max_page")
    public int maxPage;

    @JsonProperty("orders")
    public Bl3pUserTransaction[] transactions;
  }

  public static class Bl3pUserTransaction {

    @JsonProperty("transaction_id")
    public int id;

    @JsonProperty("amount")
    public Bl3pAmountObj amount;

    @JsonProperty("date")
    public Date date;

    @JsonProperty("debit_credit")
    public String debitCredit;

    @JsonProperty("price")
    public Bl3pAmountObj price;

    @JsonProperty("order_id")
    public int orderId;

    @JsonProperty("type")
    public String type;

    @JsonProperty("balance")
    public Bl3pAmountObj balance;

    @JsonProperty("trade_id")
    public int tradeId;

    @JsonProperty("contra_amount")
    public Bl3pAmountObj contraAmount;

    @JsonProperty("fee")
    public Bl3pAmountObj fee;
  }
}
