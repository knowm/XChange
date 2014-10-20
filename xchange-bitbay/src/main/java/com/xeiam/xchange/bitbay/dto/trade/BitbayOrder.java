package com.xeiam.xchange.bitbay.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 17/10/14.
 */
public class BitbayOrder {

/*
    [
    {"order_id":199121,
    "order_currency":null,
    "order_date":"2014-10-17 13:29:32",
    "payment_currency":null,
    "type":"ask",
    "status":"active",
    "units":"0.01000000",
    "price":"13.00000000",
    "total":13}
    ]
*/

    private String orderId;
    private String orderCurrency;
    private String orderDate;
    private String paymentCurrency;
    private String type;
    private String status;
    private BigDecimal units;
    private BigDecimal price;
    private BigDecimal total;

    public BitbayOrder(@JsonProperty("order_id") String orderId,
                       @JsonProperty("order_currency") String orderCurrency,
                       @JsonProperty("order_date") String orderDate,
                       @JsonProperty("payment_currency") String paymentCurrency,
                       @JsonProperty("type") String type,
                       @JsonProperty("status") String status,
                       @JsonProperty("units") BigDecimal units,
                       @JsonProperty("price") BigDecimal price,
                       @JsonProperty("total") BigDecimal total) {
        this.orderId = orderId;
        this.orderCurrency = orderCurrency;
        this.orderDate = orderDate;
        this.paymentCurrency = paymentCurrency;
        this.type = type;
        this.status = status;
        this.units = units;
        this.price = price;
        this.total = total;
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

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
