package com.okcoin.commons.okex.open.api.bean.spot.param;

public class PlaceOrderParam {
    /**
     * 客户端下单 标示id 非必填
     */
    private String client_oid;
    /**
     * 币对如 etc_eth
     */
    private String instrument_id;
    /**
     * 买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 限价单 limit 市价单 market
     */
    private String type;
    /**
     * 交易数量
     */
    private String size;
    /**
     * 限价单使用 价格
     */
    private String price;
    /**
     * 市价单使用 价格
     */
    private String notional;

    /**
     * 来源（web app ios android）
     */
    private Byte source = 0;

    /**
     * 1币币交易 2杠杆交易
     */
    private Byte margin_trading;

    public String getClient_oid() {
        return this.client_oid;
    }

    public void setClient_oid(final String client_oid) {
        this.client_oid = client_oid;
    }


    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
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

    public String getSize() {
        return this.size;
    }

    public void setSize(final String size) {
        this.size = size;
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

    public Byte getSource() {
        return this.source;
    }

    public void setSource(final Byte source) {
        this.source = source;
    }


    public Byte getMargin_trading() {
        return this.margin_trading;
    }

    public void setMargin_trading(final Byte margin_trading) {
        this.margin_trading = margin_trading;
    }
}
