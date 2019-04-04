package com.okcoin.commons.okex.open.api.bean.spot.result;

import java.util.List;

public class BatchOrdersResult {

    private boolean result;
    private List<String> order_id;
    private String client_oid;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public List<String> getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(final List<String> order_id) {
        this.order_id = order_id;
    }

    public String getClient_oid() {
        return this.client_oid;
    }

    public void setClient_oid(final String client_oid) {
        this.client_oid = client_oid;
    }
}
