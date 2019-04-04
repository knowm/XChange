package com.okcoin.commons.okex.open.api.bean.spot.result;


public class RepaymentResult {

    private boolean result;
    private Long repayment_id;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public Long getRepayment_id() {
        return this.repayment_id;
    }

    public void setRepayment_id(final Long repayment_id) {
        this.repayment_id = repayment_id;
    }

}
