package org.knowm.xchange.bl3p.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class Bl3pTradeHistoryParams implements TradeHistoryParams {
    private String currency = "BTC";
    private int page = 1;

    public String getCurrency() {
        return this.currency;
    }

    public int getPage() {
        return page;
    }

    public Bl3pTradeHistoryParams withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Bl3pTradeHistoryParams withPage(int page) {
        this.page = page;
        return this;
    }
}
