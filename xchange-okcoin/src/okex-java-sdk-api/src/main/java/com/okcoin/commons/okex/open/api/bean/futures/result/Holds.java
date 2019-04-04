package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * All of contract position  <br/>
 * Created by Tony Tian on 2018/2/26 16:14. <br/>
 */
public class Holds {
    /**
     * The id of the futures contract
     */
    private String instrument_id;
    /**
     * all of position
     */
    private Integer amount;

    private String timestamp;

    public String getInstrument_id() { return instrument_id; }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
