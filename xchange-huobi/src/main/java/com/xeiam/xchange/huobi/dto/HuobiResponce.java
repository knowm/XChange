package com.xeiam.xchange.huobi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 25/06/14
 * Time: 13:22
 */
public class HuobiResponce {

    private Integer code;
    private String msg;

    public HuobiResponce(@JsonProperty("code") Integer code,
                         @JsonProperty("msg") String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
