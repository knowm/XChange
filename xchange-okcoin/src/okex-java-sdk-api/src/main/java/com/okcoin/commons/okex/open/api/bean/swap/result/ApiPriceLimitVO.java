package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiPriceLimitVO {

    private String instrument_id;
    private String highest;
    private String lowest;
    private String timestamp;

    public ApiPriceLimitVO() {
    }

    public ApiPriceLimitVO(String instrument_id, String highest, String lowest, String timestamp) {
        this.instrument_id = instrument_id;
        this.highest = highest;
        this.lowest = lowest;
        this.timestamp = timestamp;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
