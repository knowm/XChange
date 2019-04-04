package com.okcoin.commons.okex.open.api.bean.spot.param;

public class LoanRequestDTO {
    private Integer productId;
    private Integer currencyId;
    private String amount;

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public Integer getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(final Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }
}
