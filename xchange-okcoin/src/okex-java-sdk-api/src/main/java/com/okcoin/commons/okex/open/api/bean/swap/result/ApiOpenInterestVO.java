package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiOpenInterestVO {

    private String instrument_id;
    private String amount;
    private String timestamp;

    public ApiOpenInterestVO() {
    }

    public ApiOpenInterestVO(String instrument_id, String amount, String timestamp) {
        this.instrument_id = instrument_id;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
