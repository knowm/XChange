package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiFundingTimeVO {

    private String instrument_id;
    private String funding_time;

    public ApiFundingTimeVO() {
    }

    public ApiFundingTimeVO(String instrument_id, String funding_time) {
        this.instrument_id = instrument_id;
        this.funding_time = funding_time;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getFunding_time() {
        return funding_time;
    }

    public void setFunding_time(String funding_time) {
        this.funding_time = funding_time;
    }

}
