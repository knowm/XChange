package org.knowm.xchange.getbtc.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetbtcOrderbook {

    @JsonProperty("order-book")
    private OrderBook orderBook;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetbtcOrderbook() {
    }

    /**
     * 
     * @param orderBook
     */
    public GetbtcOrderbook(OrderBook orderBook) {
        super();
        this.orderBook = orderBook;
    }

    @JsonProperty("order-book")
    public OrderBook getOrderBook() {
        return orderBook;
    }

    @JsonProperty("order-book")
    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("orderBook", orderBook).toString();
    }

//----------------------------------------    
    public static class OrderBook {

        @JsonProperty("request_currency")
        private String requestCurrency;
        @JsonProperty("bid")
        private List<Bid> bid = null;
        @JsonProperty("ask")
        private List<Ask> ask = null;

        /**
         * No args constructor for use in serialization
         * 
         */
        public OrderBook() {
        }

        /**
         * 
         * @param requestCurrency
         * @param ask
         * @param bid
         */
        public OrderBook(String requestCurrency, List<Bid> bid, List<Ask> ask) {
            super();
            this.requestCurrency = requestCurrency;
            this.bid = bid;
            this.ask = ask;
        }

        @JsonProperty("request_currency")
        public String getRequestCurrency() {
            return requestCurrency;
        }

        @JsonProperty("request_currency")
        public void setRequestCurrency(String requestCurrency) {
            this.requestCurrency = requestCurrency;
        }

        @JsonProperty("bid")
        public List<Bid> getBid() {
            return bid;
        }

        @JsonProperty("bid")
        public void setBid(List<Bid> bid) {
            this.bid = bid;
        }

        @JsonProperty("ask")
        public List<Ask> getAsk() {
            return ask;
        }

        @JsonProperty("ask")
        public void setAsk(List<Ask> ask) {
            this.ask = ask;
        }

        public static class Ask {

            @JsonProperty("price")
            private BigDecimal price;
            @JsonProperty("order_amount")
            private BigDecimal orderAmount;
            @JsonProperty("order_value")
            private BigDecimal orderValue;
            @JsonProperty("converted_from")
            private String convertedFrom;
            @JsonProperty("timestamp")
            private long timestamp;

            /**
             * No args constructor for use in serialization
             * 
             */
            public Ask() {
            }

            /**
             * 
             * @param timestamp
             * @param convertedFrom
             * @param orderValue
             * @param price
             * @param orderAmount
             */
            public Ask(BigDecimal price, BigDecimal orderAmount, BigDecimal orderValue, String convertedFrom, long timestamp) {
                super();
                this.price = price;
                this.orderAmount = orderAmount;
                this.orderValue = orderValue;
                this.convertedFrom = convertedFrom;
                this.timestamp = timestamp;
            }

            @JsonProperty("price")
            public BigDecimal getPrice() {
                return price;
            }

            @JsonProperty("price")
            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            @JsonProperty("order_amount")
            public BigDecimal getOrderAmount() {
                return orderAmount;
            }

            @JsonProperty("order_amount")
            public void setOrderAmount(BigDecimal orderAmount) {
                this.orderAmount = orderAmount;
            }

            @JsonProperty("order_value")
            public BigDecimal getOrderValue() {
                return orderValue;
            }

            @JsonProperty("order_value")
            public void setOrderValue(BigDecimal orderValue) {
                this.orderValue = orderValue;
            }

            @JsonProperty("converted_from")
            public String getConvertedFrom() {
                return convertedFrom;
            }

            @JsonProperty("converted_from")
            public void setConvertedFrom(String convertedFrom) {
                this.convertedFrom = convertedFrom;
            }

            @JsonProperty("timestamp")
            public long getTimestamp() {
                return timestamp;
            }

            @JsonProperty("timestamp")
            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this).append("price", price).append("orderAmount", orderAmount).append("orderValue", orderValue).append("convertedFrom", convertedFrom).append("timestamp", timestamp).toString();
            }

        }
        
        public static class Bid {

            @JsonProperty("price")
            private BigDecimal price;
            @JsonProperty("order_amount")
            private BigDecimal orderAmount;
            @JsonProperty("order_value")
            private BigDecimal orderValue;
            @JsonProperty("converted_from")
            private String convertedFrom;
            @JsonProperty("timestamp")
            private long timestamp;

            /**
             * No args constructor for use in serialization
             * 
             */
            public Bid() {
            }

            /**
             * 
             * @param timestamp
             * @param convertedFrom
             * @param orderValue
             * @param price
             * @param orderAmount
             */
            public Bid(BigDecimal price, BigDecimal orderAmount, BigDecimal orderValue, String convertedFrom, long timestamp) {
                super();
                this.price = price;
                this.orderAmount = orderAmount;
                this.orderValue = orderValue;
                this.convertedFrom = convertedFrom;
                this.timestamp = timestamp;
            }

            @JsonProperty("price")
            public BigDecimal getPrice() {
                return price;
            }

            @JsonProperty("price")
            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            @JsonProperty("order_amount")
            public BigDecimal getOrderAmount() {
                return orderAmount;
            }

            @JsonProperty("order_amount")
            public void setOrderAmount(BigDecimal orderAmount) {
                this.orderAmount = orderAmount;
            }

            @JsonProperty("order_value")
            public BigDecimal getOrderValue() {
                return orderValue;
            }

            @JsonProperty("order_value")
            public void setOrderValue(BigDecimal orderValue) {
                this.orderValue = orderValue;
            }

            @JsonProperty("converted_from")
            public String getConvertedFrom() {
                return convertedFrom;
            }

            @JsonProperty("converted_from")
            public void setConvertedFrom(String convertedFrom) {
                this.convertedFrom = convertedFrom;
            }

            @JsonProperty("timestamp")
            public long getTimestamp() {
                return timestamp;
            }

            @JsonProperty("timestamp")
            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this).append("price", price).append("orderAmount", orderAmount).append("orderValue", orderValue).append("convertedFrom", convertedFrom).append("timestamp", timestamp).toString();
            }

        }        
     //----- 
    }        
}