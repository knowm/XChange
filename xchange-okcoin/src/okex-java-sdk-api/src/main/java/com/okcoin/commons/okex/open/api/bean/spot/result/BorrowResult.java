package com.okcoin.commons.okex.open.api.bean.spot.result;


public class BorrowResult {

    private boolean result;
    private Long borrow_id;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public Long getBorrow_id() {
        return this.borrow_id;
    }

    public void setBorrow_id(final Long borrow_id) {
        this.borrow_id = borrow_id;
    }

}
