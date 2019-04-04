package com.okcoin.commons.okex.open.api.bean.spot.result;

public class KlineDto {
    private String time;
    private String low;
    private String high;
    private String open;
    private String close;
    private String volume;

    public String getTime() {
        return this.time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(final String low) {
        this.low = low;
    }

    public String getHigh() {
        return this.high;
    }

    public void setHigh(final String high) {
        this.high = high;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(final String open) {
        this.open = open;
    }

    public String getClose() {
        return this.close;
    }

    public void setClose(final String close) {
        this.close = close;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolume(final String volume) {
        this.volume = volume;
    }
}
