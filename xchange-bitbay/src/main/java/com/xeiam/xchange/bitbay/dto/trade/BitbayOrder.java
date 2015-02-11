package com.xeiam.xchange.bitbay.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 17/10/14.
 */
public class BitbayOrder {

    private String orderId;
    private String orderCurrency;
    private String orderDate;
    private String paymentCurrency;
    private String type;
    private String status;
    private BigDecimal units;
    private BigDecimal startUnits;
    private BigDecimal currentPrice;
    private BigDecimal startPrice;

    public BitbayOrder(@JsonProperty("order_id") String orderId,
                       @JsonProperty("order_currency") String orderCurrency,
                       @JsonProperty("order_date") String orderDate,
                       @JsonProperty("payment_currency") String paymentCurrency,
                       @JsonProperty("type") String type,
                       @JsonProperty("status") String status,
                       @JsonProperty("units") BigDecimal units,
                       @JsonProperty("start_units") BigDecimal startUnits,
                       @JsonProperty("current_price") BigDecimal currentPrice,
                       @JsonProperty("start_price") BigDecimal startPrice) {
        this.orderId = orderId;
        this.orderCurrency = orderCurrency;
        this.orderDate = orderDate;
        this.paymentCurrency = paymentCurrency;
        this.type = type;
        this.status = status;
        this.units = units;
        this.startUnits = startUnits;
        this.currentPrice = currentPrice;
        this.startPrice = startPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public BigDecimal getStartUnits() {
        return startUnits;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

}
