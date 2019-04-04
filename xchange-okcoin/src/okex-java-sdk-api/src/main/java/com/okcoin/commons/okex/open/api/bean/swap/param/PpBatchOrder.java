package com.okcoin.commons.okex.open.api.bean.swap.param;

public class PpBatchOrder {

    /**
     * 由您设置的订单id来唯一标识您的订单
     */
    private String client_oid;
    /**
     * 下单数量
     */
    private String size;
    /**
     * 1:开多 2:开空 3:平多 4:平空
     */
    private String type;
    /**
     * 是否以对手价下单 0:不是 1:是
     */
    private String match_price;
    /**
     * 委托价格
     */
    private String price;

    public PpBatchOrder() {
    }

    public PpBatchOrder(String client_oid, String size, String type, String match_price, String price) {
        this.client_oid = client_oid;
        this.size = size;
        this.type = type;
        this.match_price = match_price;
        this.price = price;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatch_price() {
        return match_price;
    }

    public void setMatch_price(String match_price) {
        this.match_price = match_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
