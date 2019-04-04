package com.okcoin.commons.okex.open.api.bean.spot.result;

public class Currency {
    private String currency_id;
    private String name;

    public String getCurrency_id() {
        return this.currency_id;
    }

    public void setCurrency_id(final String currency_id) {
        this.currency_id = currency_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
