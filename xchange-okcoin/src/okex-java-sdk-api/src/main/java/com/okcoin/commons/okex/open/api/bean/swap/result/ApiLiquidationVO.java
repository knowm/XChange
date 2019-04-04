package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiLiquidationVO {

    private String instrument_id;
    private String size;
    private String created_at;
    private String loss;
    private String price;
    private String type;

    public ApiLiquidationVO() {
    }

    public ApiLiquidationVO(String instrument_id, String size, String created_at, String loss, String price, String type) {
        this.instrument_id = instrument_id;
        this.size = size;
        this.created_at = created_at;
        this.loss = loss;
        this.price = price;
        this.type = type;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
