package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiDealVO {

    private String trade_id;
    private String price;
    private String amount;
    private String side;
    private String timestamp;

    public ApiDealVO(String trade_id, String price, String amount, String side, String timestamp) {
        this.trade_id = trade_id;
        this.price = price;
        this.amount = amount;
        this.side = side;
        this.timestamp = timestamp;
    }

    public ApiDealVO() {
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
