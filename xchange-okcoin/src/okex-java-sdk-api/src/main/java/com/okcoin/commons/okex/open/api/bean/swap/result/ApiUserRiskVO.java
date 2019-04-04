package com.okcoin.commons.okex.open.api.bean.swap.result;

import java.math.BigDecimal;

public class ApiUserRiskVO {

    private BigDecimal long_leverage;
    private BigDecimal short_leverage;
    private String margin_mode;
    private String instrument_id;

    public ApiUserRiskVO() {
    }

    public ApiUserRiskVO(BigDecimal long_leverage, BigDecimal short_leverage, String margin_mode, String instrument_id) {
        this.long_leverage = long_leverage;
        this.short_leverage = short_leverage;
        this.margin_mode = margin_mode;
        this.instrument_id = instrument_id;
    }

    public BigDecimal getLong_leverage() {
        return long_leverage;
    }

    public void setLong_leverage(BigDecimal long_leverage) {
        this.long_leverage = long_leverage;
    }

    public BigDecimal getShort_leverage() {
        return short_leverage;
    }

    public void setShort_leverage(BigDecimal short_leverage) {
        this.short_leverage = short_leverage;
    }

    public String getMargin_mode() {
        return margin_mode;
    }

    public void setMargin_mode(String margin_mode) {
        this.margin_mode = margin_mode;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

}
