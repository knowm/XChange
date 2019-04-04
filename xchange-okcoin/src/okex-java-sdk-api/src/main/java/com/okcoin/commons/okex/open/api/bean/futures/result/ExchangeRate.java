package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Exchange Rate
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:31
 */
public class ExchangeRate {
    /**
     * legal tender pairs
     */
    private String product_id;
    /**
     * exchange rate
     */
    private Double rate;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
