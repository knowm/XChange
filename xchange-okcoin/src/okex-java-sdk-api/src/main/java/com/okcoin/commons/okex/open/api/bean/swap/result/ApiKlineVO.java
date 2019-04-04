package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiKlineVO {

    private String timestamp;
    private String low;
    private String high;
    private String open;
    private String close;
    private String volume;
    private String currency_volume;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCurrency_volume() {
        return currency_volume;
    }

    public void setCurrency_volume(String currency_volume) {
        this.currency_volume = currency_volume;
    }

    public ApiKlineVO() {
    }

    public ApiKlineVO(String timestamp, String low, String high, String open, String close, String volume, String currency_volume) {
        this.timestamp = timestamp;
        this.low = low;
        this.high = high;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.currency_volume = currency_volume;
    }
}
