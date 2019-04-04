package com.okcoin.commons.okex.open.api.bean.spot.result;

public class MarginBorrowOrderDto {
    private Long borrow_id;
    private String product_id;
    private String instrument_id;
    private String currency;
    private String timestamp;
    private String created_at;
    private String last_interest_time;
    private String amount;
    private String interest;
    private String repay_amount;
    private String returned_amount;
    private String repay_interest;
    private String paid_interest;

    public Long getBorrow_id() {
        return this.borrow_id;
    }

    public void setBorrow_id(final Long borrow_id) {
        this.borrow_id = borrow_id;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(final String product_id) {
        this.product_id = product_id;
    }

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(final String created_at) {
        this.created_at = created_at;
    }

    public String getLast_interest_time() {
        return this.last_interest_time;
    }

    public void setLast_interest_time(final String last_interest_time) {
        this.last_interest_time = last_interest_time;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return this.interest;
    }

    public void setInterest(final String interest) {
        this.interest = interest;
    }

    public String getRepay_amount() {
        return this.repay_amount;
    }

    public void setRepay_amount(final String repay_amount) {
        this.repay_amount = repay_amount;
    }

    public String getReturned_amount() {
        return this.returned_amount;
    }

    public void setReturned_amount(final String returned_amount) {
        this.returned_amount = returned_amount;
    }

    public String getRepay_interest() {
        return this.repay_interest;
    }

    public void setRepay_interest(final String repay_interest) {
        this.repay_interest = repay_interest;
    }

    public String getPaid_interest() {
        return this.paid_interest;
    }

    public void setPaid_interest(final String paid_interest) {
        this.paid_interest = paid_interest;
    }
}
