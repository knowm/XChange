package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiContractVO {

    private String instrument_id;
    private String underlying_index;
    private String quote_currency;
    private String coin;
    private String contract_val;
    private String listing;
    private String delivery;
    private String size_increment;
    private String tick_size;

    public ApiContractVO(String instrument_id, String underlying_index, String quote_currency, String coin, String contract_val, String listing, String delivery, String size_increment, String tick_size) {
        this.instrument_id = instrument_id;
        this.underlying_index = underlying_index;
        this.quote_currency = quote_currency;
        this.coin = coin;
        this.contract_val = contract_val;
        this.listing = listing;
        this.delivery = delivery;
        this.size_increment = size_increment;
        this.tick_size = tick_size;
    }

    public ApiContractVO() {
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getUnderlying_index() {
        return underlying_index;
    }

    public void setUnderlying_index(String underlying_index) {
        this.underlying_index = underlying_index;
    }

    public String getQuote_currency() {
        return quote_currency;
    }

    public void setQuote_currency(String quote_currency) {
        this.quote_currency = quote_currency;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getContract_val() {
        return contract_val;
    }

    public void setContract_val(String contract_val) {
        this.contract_val = contract_val;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getSize_increment() {
        return size_increment;
    }

    public void setSize_increment(String size_increment) {
        this.size_increment = size_increment;
    }

    public String getTick_size() {
        return tick_size;
    }

    public void setTick_size(String tick_size) {
        this.tick_size = tick_size;
    }

}
