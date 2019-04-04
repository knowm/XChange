package com.okcoin.commons.okex.open.api.bean.spot.param;

import java.util.List;

public class OrderParamDto {
    private String instrument_id;
    private List<Long> order_ids;

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }


    public List<Long> getOrder_ids() {
        return this.order_ids;
    }

    public void setOrder_ids(final List<Long> order_ids) {
        this.order_ids = order_ids;
    }
}
