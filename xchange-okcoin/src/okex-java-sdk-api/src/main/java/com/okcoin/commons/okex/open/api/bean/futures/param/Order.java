package com.okcoin.commons.okex.open.api.bean.futures.param;

/**
 * New Order
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 15:38
 */
public class Order {
    /**
     * The id of the futures, eg: BTC-USD-180629
     */
    protected String instrument_id;
    /**
     * lever, default 10.
     */
    protected Double leverage;
    /**
     * You setting order id.(optional)
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

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setinstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }


    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }
    public Double getLeverage() {
        return leverage;
    }

    public void setLeverage(Double leverage) {
        this.leverage = leverage;
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
