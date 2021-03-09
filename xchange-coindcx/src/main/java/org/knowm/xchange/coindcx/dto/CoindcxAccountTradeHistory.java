package org.knowm.xchange.coindcx.dto;

import java.util.Date;

public class CoindcxAccountTradeHistory {

    private String from_id;
    private int limit;
    private Long timestamp;

    public CoindcxAccountTradeHistory(String fromId,int limit){
        this.from_id=fromId;
        this.limit=limit;
        this.timestamp=new Date().getTime();
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CoindcxAccountTradeHistory{" +
                "from_id='" + from_id + '\'' +
                ", limit=" + limit +
                ", timestamp=" + timestamp +
                '}';
    }
}
