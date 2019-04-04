package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * all of contract liquidation <br/>
 * Created by Tony Tian on 2018/2/26 16:36. <br/>
 */
public class Liquidation {
    /**
     * The id of the futures contract
     */
    private String instrument_id;
    /**
     * order price
     */
    private Double price;
    /**
     * order quantity(unit: contract)
     */
    private Double size;
    /**
     * The execution type {@link com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum}
     */
    private Integer type;
    /**
     * user loss due to forced liquidation
     */
    private Double loss;
    /**
     * create date
     */
    private String created_at;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public String getInstrument_id() { return instrument_id; }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public Double getSize() { return size; }

    public void setSize(Double size) { this.size = size; }
    public String getCreated_at() { return created_at; }

    public void setCreated_at(String created_at) { this.created_at = created_at; }
}
