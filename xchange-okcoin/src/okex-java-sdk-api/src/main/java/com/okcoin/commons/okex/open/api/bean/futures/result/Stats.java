package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Get 24 hours of contract data.  <br/>
 * Created by Tony Tian on 2018/2/26 13:59. <br/>
 */
public class Stats {
    /**
     * The id of the futures contract
     */
    private String product_id;
    /**
     * Opening price
     */
    private Double open;
    /**
     * 24 hours of highest price
     */
    private Double high;
    /**
     * 24 hours of lowest price
     */
    private Double low;
    /**
     * Volume of sheet
     */
    private Double volume;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
