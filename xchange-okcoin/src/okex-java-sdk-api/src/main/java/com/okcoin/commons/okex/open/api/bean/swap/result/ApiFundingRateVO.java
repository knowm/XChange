package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiFundingRateVO {

    private String instrument_id;
    private String funding_rate;
    private String funding_rate_new;
    private String interest_rate;
    private String funding_time;

    public ApiFundingRateVO() {
    }

    public ApiFundingRateVO(String instrument_id, String funding_rate, String funding_rate_new, String interest_rate, String funding_time) {
        this.instrument_id = instrument_id;
        this.funding_rate = funding_rate;
        this.funding_rate_new = funding_rate_new;
        this.interest_rate = interest_rate;
        this.funding_time = funding_time;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getFunding_rate() {
        return funding_rate;
    }

    public void setFunding_rate(String funding_rate) {
        this.funding_rate = funding_rate;
    }

    public String getFunding_rate_new() {
        return funding_rate_new;
    }

    public void setFunding_rate_new(String funding_rate_new) {
        this.funding_rate_new = funding_rate_new;
    }

    public String getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(String interest_rate) {
        this.interest_rate = interest_rate;
    }

    public String getFunding_time() {
        return funding_time;
    }

    public void setFunding_time(String funding_time) {
        this.funding_time = funding_time;
    }

}
