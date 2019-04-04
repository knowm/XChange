package com.okcoin.commons.okex.open.api.bean.swap.result;

import java.util.List;

public class DepthVO {

    private List<Object[]> asks;
    private List<Object[]> bids;
    private String timestamp;

    public DepthVO(List<Object[]> asks, List<Object[]> bids, String timeStamp) {
        this.asks = asks;
        this.bids = bids;
        this.timestamp = timeStamp;
    }

    public DepthVO() {
    }

    public List<Object[]> getAsks() {
        return asks;
    }

    public void setAsks(List<Object[]> asks) {
        this.asks = asks;
    }

    public List<Object[]> getBids() {
        return bids;
    }

    public void setBids(List<Object[]> bids) {
        this.bids = bids;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

}
