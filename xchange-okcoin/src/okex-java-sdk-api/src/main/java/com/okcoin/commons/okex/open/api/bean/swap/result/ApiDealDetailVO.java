package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiDealDetailVO {

    private Long trade_id;
    private String instrument_id;
    private String order_id;
    private String price;
    private String order_qty;
    private String fee;
    private String timestamp;
    private String exec_type;
    private String side;

    public ApiDealDetailVO() {
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_qty() {
        return order_qty;
    }

    public void setOrder_qty(String order_qty) {
        this.order_qty = order_qty;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getExec_type() {
        return exec_type;
    }

    public void setExec_type(String exec_type) {
        this.exec_type = exec_type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public ApiDealDetailVO(Long trade_id, String instrument_id, String order_id, String price, String order_qty, String fee, String timestamp, String exec_type, String side) {
        this.trade_id = trade_id;
        this.instrument_id = instrument_id;
        this.order_id = order_id;
        this.price = price;
        this.order_qty = order_qty;
        this.fee = fee;
        this.timestamp = timestamp;
        this.exec_type = exec_type;
        this.side = side;
    }
}
