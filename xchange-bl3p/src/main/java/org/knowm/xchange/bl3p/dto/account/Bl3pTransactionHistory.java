package org.knowm.xchange.bl3p.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pAmountObj;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

import java.util.Date;

public class Bl3pTransactionHistory extends Bl3pResult<Bl3pTransactionHistory.Bl3pTransactionHistoryData> {

    public static class Bl3pTransactionHistoryData {

        @JsonProperty("page")
        private int page;

        @JsonProperty("records")
        private int records;

        @JsonProperty("max_page")
        private int maxPage;

        @JsonProperty("transactions")
        private Bl3pTransactionHistoryTransaction[] transactions;

        public int getPage() {
            return page;
        }

        public int getRecords() {
            return records;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public Bl3pTransactionHistoryTransaction[] getTransactions() {
            return transactions;
        }
    }

    public static class Bl3pTransactionHistoryTransaction {

        @JsonProperty("transaction_id")
        private int transactionId;

        @JsonProperty("amount")
        private Bl3pAmountObj amount;

        private Date date;

        @JsonProperty("credit_debit")
        private String creditDebit;

        @JsonProperty("price")
        private Bl3pAmountObj price;

        @JsonProperty("order_id")
        private int orderId;

        @JsonProperty("type")
        private String type;

        @JsonProperty("balance")
        private Bl3pAmountObj balance;

        @JsonProperty("trade_id")
        private int tradeId;

        @JsonProperty("contra_amount")
        private Bl3pAmountObj contraAmount;

        @JsonProperty("fee")
        private Bl3pAmountObj fee;

        public Bl3pTransactionHistoryTransaction(@JsonProperty("date") long date) {
            this.date = new Date(date * 1000l);
        }

        public int getTransactionId() {
            return transactionId;
        }

        public Bl3pAmountObj getAmount() {
            return amount;
        }

        public Date getDate() {
            return date;
        }

        public String getCreditDebit() {
            return creditDebit;
        }

        public Bl3pAmountObj getPrice() {
            return price;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getType() {
            return type;
        }

        public Bl3pAmountObj getBalance() {
            return balance;
        }

        public int getTradeId() {
            return tradeId;
        }

        public Bl3pAmountObj getContraAmount() {
            return contraAmount;
        }

        public Bl3pAmountObj getFee() {
            return fee;
        }
    }
}
