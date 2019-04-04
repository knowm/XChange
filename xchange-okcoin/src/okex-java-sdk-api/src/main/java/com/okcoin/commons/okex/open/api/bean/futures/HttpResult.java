package com.okcoin.commons.okex.open.api.bean.futures;

/**
 * Http Result
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 17/03/2018 11:36
 */
public class HttpResult {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
