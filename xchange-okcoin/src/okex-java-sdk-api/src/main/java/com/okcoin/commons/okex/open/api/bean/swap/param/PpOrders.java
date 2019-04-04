package com.okcoin.commons.okex.open.api.bean.swap.param;

import java.util.List;

public class PpOrders {

    private String instrument_id = "";
    private List<PpBatchOrder> order_data = null;

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public void setOrder_data(List<PpBatchOrder> order_data) {
        this.order_data = order_data;
    }


    public String getInstrument_id() {
        return instrument_id;
    }

    public List<PpBatchOrder> getOrder_data() {
        return order_data;
    }

    public PpOrders() {
    }

    public PpOrders(String instrumentId, List<PpBatchOrder> orderData) {
        this.instrument_id = instrumentId;
        this.order_data = orderData;
    }
}
