package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Contract price estimate for delivery.  <br/>
 * Created by Tony Tian on 2018/2/26 15:52. <br/>
 */
public class EstimatedPrice {
    /**
     * The id of the futures contract
     */
    private String instrument_id;
    /**
     * Estimated price
     */
    private Double settlement_price;
    /**
     * time
     */
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstrument_id() { return instrument_id; }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public Double getSettlement_price() { return settlement_price; }

    public void setSettlement_price(Double settlement_price) { this.settlement_price = settlement_price; }
}
