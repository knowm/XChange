package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttCreateOrderResult {

    private String order_id;
    private String client_oid;
    private Boolean result;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttCreateOrderResult that = (EttCreateOrderResult)o;
        return Objects.equals(order_id, that.order_id) &&
            Objects.equals(client_oid, that.client_oid) &&
            Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, client_oid, result);
    }

    @Override
    public String toString() {
        return "EttCreateOrderResult{" +
            "order_id='" + order_id + '\'' +
            ", client_oid='" + client_oid + '\'' +
            ", result=" + result +
            '}';
    }
}
