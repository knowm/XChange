package com.okcoin.commons.okex.open.api.bean.spot.result;

public class OrderInfo {

    /**
     * 订单id
     */
    private Long order_id;
    /**
     * limit 订单类型的价格信息
     */
    private String price;
    /**
     * market 订单类型的价格信息
     */
    private String notional;
    /**
     * 委托数量
     */
    private String size;
    /**
     * 平均成交价
     */
    //private String avg_price;
    /**
     * 委托时间
     */
    private String timestamp;
    /**
     * 成交数量
     */
    private String filled_size;
    /**
     * 订单状态 -1 已撤销 0 未成交
     */
    private String status;
    /**
     * 订单买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 limit/market
     */
    private String type;
    /**
     * 币对信息
     */
    private String instrument_id;

    /**
     * 计价成交量
     */
    private String filled_notional;

    public Long getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(final Long order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }



    public String getNotional() {
        return this.notional;
    }

    public void setNotional(final String notional) {
        this.notional = notional;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(final String size) {
        this.size = size;
    }


    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilled_size() {
        return this.filled_size;
    }

    public void setFilled_size(final String filled_size) {
        this.filled_size = filled_size;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(final String side) {
        this.side = side;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }





    public String getFilled_notional() {
        return this.filled_notional;
    }

    public void setFilled_notional(final String filled_notional) {
        this.filled_notional = filled_notional;
    }
}
