package org.knowm.xchange.coindcx.dto;

public class CoindcxTickersResponse {

    private String market;
    private String change_24_hour;
    private String high;
    private String low;
    private String volume;
    private String last_price;
    private String bid;
    private String ask;
    private Long timestamp;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getChange_24_hour() {
        return change_24_hour;
    }

    public void setChange_24_hour(String change_24_hour) {
        this.change_24_hour = change_24_hour;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLast_price() {
        return last_price;
    }

    public void setLast_price(String last_price) {
        this.last_price = last_price;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CoindcxTickersResponse{" +
                "market='" + market + '\'' +
                ", change_24_hour='" + change_24_hour + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", volume='" + volume + '\'' +
                ", last_price='" + last_price + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
