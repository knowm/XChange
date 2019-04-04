package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * The current limit of the contract.  <br/>
 * Created by Tony Tian on 2018/2/26 16:21. <br/>
 */
public class PriceLimit {
    /**
     * The id of the futures contract
     */
    private String instrument_id;
    /**
     * Highest price
     */
    private Double highest;
    /**
     * Lowest price
     */
    private Double lowest;

    private String timestamp;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public Double getHighest() { return highest; }

    public void setHighest(Double highest) { this.highest = highest; }

    public Double getLowest() { return lowest; }

    public void setLowest(Double lowest) { this.lowest = lowest; }
}
