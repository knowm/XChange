package com.xeiam.xchange.independentreserve.dto.trade;

import com.xeiam.xchange.independentreserve.dto.auth.AuthAggregate;

/**
 * Author: Kamil Zbikowski
 * Date: 4/15/15
 */
public class IndependentReservePlaceLimitOrderRequest extends AuthAggregate {

    public IndependentReservePlaceLimitOrderRequest(String apiKey, Long nonce,
                                                    String primaryCurrencyCode,
                                                    String secondaryCurrencyCode,
                                                    String orderType,
                                                    String price,
                                                    String volume) {
        super(apiKey, nonce);
        if(primaryCurrencyCode.equals("BTC")){
            primaryCurrencyCode = "Xbt";
        }else{
            throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of base currency code");
        }
        if(secondaryCurrencyCode.equals("USD")){
            secondaryCurrencyCode = "Usd";
        }else{
            throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of counter currency code");
        }
        this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
        this.parameters.put("secondaryCurrencyCode", secondaryCurrencyCode);
        this.parameters.put("orderType", orderType);
        this.parameters.put("price", price);
        this.parameters.put("volume", volume);
    }
}
