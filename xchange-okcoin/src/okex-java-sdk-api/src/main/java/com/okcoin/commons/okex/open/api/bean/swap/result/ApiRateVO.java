package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiRateVO {

    private String instrument_id;
    private String rate;
    private String timestamp;

    public ApiRateVO() {
    }

    public ApiRateVO(String instrument_id, String rate, String timestamp) {
        this.instrument_id = instrument_id;
        this.rate = rate;
        this.timestamp = timestamp;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
