package org.knowm.xchange.bl3p.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bl3pOrderBook {

    @JsonProperty("result")
    private String result;

    @JsonProperty("data")
    private Bl3pOrderBookData orderBookData;

    public Bl3pOrderBookData getOrderBookData() {
        return orderBookData;
    }

    public static class Bl3pOrderBookData {
        @JsonProperty("asks")
        private Bl3pOrderBookOrder[] asks;

        @JsonProperty("bids")
        private Bl3pOrderBookOrder[] bids;

        public Bl3pOrderBookOrder[] getAsks() {
            return asks;
        }

        public Bl3pOrderBookOrder[] getBids() {
            return bids;
        }
    }

    public static class Bl3pOrderBookOrder {
        @JsonProperty("count")
        private int count;

        @JsonProperty("price_int")
        private long priceInt;

        @JsonProperty("amount_int")
        private long amountInt;

        public int getCount() {
            return count;
        }

        public long getPriceInt() {
            return priceInt;
        }

        public long getAmountInt() {
            return amountInt;
        }
    }
}
