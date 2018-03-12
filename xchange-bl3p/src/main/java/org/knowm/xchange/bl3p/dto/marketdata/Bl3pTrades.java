package org.knowm.xchange.bl3p.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Bl3pTrades {

    @JsonProperty("result")
    private String result;

    @JsonProperty("data")
    private Bl3pTradesData data;

    public Bl3pTradesData getData() {
        return data;
    }

    public static class Bl3pTradesData {
        @JsonProperty("trades")
        private Bl3pTrade[] trades;

        public Bl3pTrade[] getTrades() {
            return trades;
        }
    }

    public static class Bl3pTrade {
        @JsonProperty("date")
        private Date date;

        @JsonProperty("trade_id")
        private long tradeId;

        @JsonProperty("price_int")
        private long priceInt;

        @JsonProperty("amount_int")
        private long amountInt;

        public Date getDate() {
            return date;
        }

        public long getTradeId() {
            return tradeId;
        }

        public long getPriceInt() {
            return priceInt;
        }

        public long getAmountInt() {
            return amountInt;
        }
    }
}
