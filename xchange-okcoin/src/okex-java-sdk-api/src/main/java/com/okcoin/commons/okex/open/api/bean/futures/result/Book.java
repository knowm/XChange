package com.okcoin.commons.okex.open.api.bean.futures.result;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * futures contract product book
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 15:14
 */
public class Book {

    /**
     * asks book
     */
    JSONArray asks;
    /**
     * bids book
     */
    JSONArray bids;
    /**
     * time
     */
    String timestamp;

    public JSONArray getAsks() { return asks; }

    public void setAsks(JSONArray asks) { this.asks = asks; }

    public JSONArray getBids() { return bids; }

    public void setBids(JSONArray bids) { this.bids = bids; }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
