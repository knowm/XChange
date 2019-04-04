package com.okcoin.commons.okex.open.api.bean.swap.result;

import java.util.LinkedList;
import java.util.List;

public class OrderCancelResult {

    private String instrument_id = "";
    private List<String> order_ids = new LinkedList<>();
    private String result = "";

    public OrderCancelResult(String instrument_id, List<String> order_ids, String result) {
        this.instrument_id = instrument_id;
        this.order_ids = order_ids;
        this.result = result;
    }

    public OrderCancelResult() {
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public List<String> getOrder_ids() {
        return order_ids;
    }

    public void setOrder_ids(List<String> order_ids) {
        this.order_ids = order_ids;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;

    }
}