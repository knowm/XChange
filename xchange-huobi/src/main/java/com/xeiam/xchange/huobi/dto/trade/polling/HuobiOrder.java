/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */package com.xeiam.xchange.huobi.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.huobi.dto.HuobiResponce;

import java.math.BigDecimal;

public final class HuobiOrder extends HuobiResponce {

//    id 	Ordersid
//    type 	1buy　2sell
//    order_price 	Order price
//    order_amount 	Order amount
//    processed_price 	Average price
//    processed_amount 	Completed amount

//    vot 	Total
//    fee 	Trading fee
//    total 	Volume
//    status 	Status　0Unfilled　1Partially filled　2Finished　3Cancelled



    // open order
    private final String id;
    private final Integer type;
    private final BigDecimal orderPrice;
    private final BigDecimal orderAmount;
    private final BigDecimal processedAmount;
    private final Long orderTime;

    // order info
    private final BigDecimal vot;
    private final BigDecimal fee;
    private final BigDecimal total;
    private final Integer status;

    public HuobiOrder(@JsonProperty("code") Integer code,
                      @JsonProperty("msg") String msg,
                      @JsonProperty("id") String id,
                      @JsonProperty("type") Integer type,
                      @JsonProperty("order_price") BigDecimal orderPrice,
                      @JsonProperty("order_amount") BigDecimal orderAmount,
                      @JsonProperty("processed_amount") BigDecimal processedAmount,
                      @JsonProperty("order_time") Long orderTime,
                      @JsonProperty("vot") BigDecimal vot,
                      @JsonProperty("fee") BigDecimal fee,
                      @JsonProperty("total") BigDecimal total,
                      @JsonProperty("status") Integer status) {
        super(code, msg);

        this.id = id;
        this.type = type;
        this.orderPrice = orderPrice;
        this.orderAmount = orderAmount;
        this.processedAmount = processedAmount;
        this.orderTime = orderTime;
        this.vot = vot;
        this.fee = fee;
        this.total = total;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public BigDecimal getProcessedAmount() {
        return processedAmount;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public BigDecimal getVot() {
        return vot;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Integer getStatus() {
        return status;
    }

    public enum Status {
        UNFILLED,
        PARTIALLY,
        FINISHED,
        CANCELLED
    }
}
