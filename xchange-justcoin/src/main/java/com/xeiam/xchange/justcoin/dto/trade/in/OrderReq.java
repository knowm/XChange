package com.xeiam.xchange.justcoin.dto.trade.in;

public class OrderReq {
    private final String market;
    private final String price; //The order price. If set to null, the order is placed as a market order.
    private final String amount;
    private final String type;
    //private final String aon = null; // Optional. If true, the order is placed as all-or-nothing. TODO: Is this implemented?

    public OrderReq(String market, String price, String amount, String type) {
        this.market = market;
        this.price = price;
        this.amount = amount;
        this.type = type;
    }

    public String getMarket() {
        return market;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

}
