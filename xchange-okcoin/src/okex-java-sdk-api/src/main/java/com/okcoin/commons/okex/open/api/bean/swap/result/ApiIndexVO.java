package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiIndexVO {

    private String instrument_id;
    private String index;
    private String timestamp;

    public ApiIndexVO() {
    }

    public ApiIndexVO(String instrument_id, String index, String timestamp) {
        this.instrument_id = instrument_id;
        this.index = index;
        this.timestamp = timestamp;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
