package com.okcoin.commons.okex.open.api.enums;

/**
 * Content-Type Enum: MediaType: Internet Media Type
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/1/31 17:49
 */
public enum ContentTypeEnum {

    APPLICATION_JSON("application/json"),
    APPLICATION_JSON_UTF8("application/json; charset=UTF-8"),
    // The server does not support types
    APPLICATION_FORM("application/x-www-form-urlencoded; charset=UTF-8"),;


    private String contentType;

    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }

    public String contentType() {
        return contentType;
    }
}
