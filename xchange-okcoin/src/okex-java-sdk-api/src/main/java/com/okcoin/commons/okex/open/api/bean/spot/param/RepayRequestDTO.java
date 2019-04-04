package com.okcoin.commons.okex.open.api.bean.spot.param;

public class RepayRequestDTO {
    private Integer productId;
    private Integer currencyId;
    private String amount;
    private Long orderId;

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

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
