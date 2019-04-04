package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiMarkPriceVO {

    private String instrument_id;
    private String mark_price;
    private String timestamp;

    public ApiMarkPriceVO() {
    }

    public ApiMarkPriceVO(String instrument_id, String mark_price, String timestamp) {
        this.instrument_id = instrument_id;
        this.mark_price = mark_price;
        this.timestamp = timestamp;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getMark_price() {
        return mark_price;
    }

    public void setMark_price(String mark_price) {
        this.mark_price = mark_price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
