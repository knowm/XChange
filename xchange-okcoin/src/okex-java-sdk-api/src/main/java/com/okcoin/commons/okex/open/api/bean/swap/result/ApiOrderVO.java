package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiOrderVO {


    private String order_id;
    private String client_oid;
    private String error_code;
    private String error_message;
    private String result;

    public ApiOrderVO() {
    }

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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ApiOrderVO(String order_id, String client_oid, String error_code, String error_message, String result) {
        this.order_id = order_id;
        this.client_oid = client_oid;
        this.error_code = error_code;
        this.error_message = error_message;
        this.result = result;
    }
}
