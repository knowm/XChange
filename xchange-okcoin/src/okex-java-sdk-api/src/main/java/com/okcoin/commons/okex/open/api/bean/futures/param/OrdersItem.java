package com.okcoin.commons.okex.open.api.bean.futures.param;

/**
 * batch new order sub element <br/>
 * Created by Tony Tian on 2018/2/28 17:57. <br/>
 */
public class OrdersItem {
    /**
     * You setting order id. (optional)
     */
    private String client_oid;
    /**
     * The execution type {@link com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum}
     */
    private Integer type;
    /**
     * The order price: Maximum 1 million
     */
    private Double price;
    /**
     * The order amount: Maximum 1 million
     */
    private Integer size;
    /**
     * Match best counter party price (BBO)? 0: No 1: Yes   If yes, the 'price' field is ignored
     */
    private Integer match_price;

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getMatch_price() {
        return match_price;
    }

    public void setMatch_price(Integer match_price) {
        this.match_price = match_price;
    }
}
