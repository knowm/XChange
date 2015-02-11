package com.xeiam.xchange.bitbay.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 17/10/14.
 */
public class BitbayTradeResponse extends BitbayBaseResponse {

    private String orderId;
//    private String fee_currency;
//    private BigDecimal amount;
//    private BigDecimal rate;
//    private BigDecimal price;
//    private BigDecimal fee;

    public BitbayTradeResponse(@JsonProperty("code") String code, @JsonProperty("message") String message, @JsonProperty("success") String success,
                               @JsonProperty("order_id") String orderId
//                               @JsonProperty("fee_currency") String fee_currency,
//                               @JsonProperty("amount") BigDecimal amount,
//                               @JsonProperty("rate") BigDecimal rate,
//                               @JsonProperty("price") BigDecimal price,
//                               @JsonProperty("fee") BigDecimal fee
                                ) {
        super(code, message, success);
        this.orderId = orderId;
//        this.fee_currency = fee_currency;
//        this.amount = amount;
//        this.rate = rate;
//        this.price = price;
//        this.fee = fee;
    }

    public String getOrderId() {
        return orderId;
    }

//    public String getFee_currency() {
//        return fee_currency;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public BigDecimal getRate() {
//        return rate;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public BigDecimal getFee() {
//        return fee;
//    }
}
