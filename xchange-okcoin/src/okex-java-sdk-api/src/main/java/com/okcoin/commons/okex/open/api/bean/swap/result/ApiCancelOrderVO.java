package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiCancelOrderVO {

    private String order_id;
    private String result;

    public ApiCancelOrderVO() {
    }

    public ApiCancelOrderVO(String order_id, String result) {
        this.order_id = order_id;
        this.result = result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
