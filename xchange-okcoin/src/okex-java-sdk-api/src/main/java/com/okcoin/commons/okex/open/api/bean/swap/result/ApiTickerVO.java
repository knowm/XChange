package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiTickerVO {

    private String instrument_id;
    private String last;
    private String high_24h;
    private String low_24h;
    private String volume_24h;
    private String timestamp;

    public ApiTickerVO(String instrument_id, String last, String high_24h, String low_24h, String volume_24h, String timestamp) {
        this.instrument_id = instrument_id;
        this.last = last;
        this.high_24h = high_24h;
        this.low_24h = low_24h;
        this.volume_24h = volume_24h;
        this.timestamp = timestamp;
    }

    public ApiTickerVO() {
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
