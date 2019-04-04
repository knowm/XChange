package com.okcoin.commons.okex.open.api.bean.spot.result;

public class Fills {

    // 账单 id
    private Long ledger_id;
    // 币种 id
    private String instrument_id;
    private String product_id;
    // 价格
    private String price;
    // 数量
    private String size;
    // 订单 id
    private Long order_id;
    // 创建时间
    private String timestamp;
    private String created_at;
    // 流动方向
    private String liquidity;
    private String exec_type;
    // 手续费
    private String fee;
    // buy、sell
    private String side;

    public Long getLedger_id() {
        return this.ledger_id;
    }

    public void setLedger_id(final Long ledger_id) {
        this.ledger_id = ledger_id;
    }

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(final String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public Long getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(final Long order_id) {
        this.order_id = order_id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(final String created_at) {
        this.created_at = created_at;
    }

    public String getLiquidity() {
        return this.liquidity;
    }

    public void setLiquidity(final String liquidity) {
        this.liquidity = liquidity;
    }

    public String getExec_type() {
        return this.exec_type;
    }

    public void setExec_type(final String exec_type) {
        this.exec_type = exec_type;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(final String fee) {
        this.fee = fee;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(final String side) {
        this.side = side;
    }
}
