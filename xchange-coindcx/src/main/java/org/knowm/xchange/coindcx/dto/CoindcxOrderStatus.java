package org.knowm.xchange.coindcx.dto;

import java.util.Date;

public class CoindcxOrderStatus {

    private String id;
    private Long timestamp;

    public CoindcxOrderStatus(String id){
        this.id=id;
        this.timestamp=new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CoindcxOrderStatus{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
