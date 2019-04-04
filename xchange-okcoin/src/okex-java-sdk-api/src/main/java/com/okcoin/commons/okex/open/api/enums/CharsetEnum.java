package com.okcoin.commons.okex.open.api.enums;

/**
 * Charset Enum
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/2/5 21:22
 */
public enum CharsetEnum {

    UTF_8("UTF-8"),
    ISO_8859_1("ISO-8859-1"),;


    private String charset;

    CharsetEnum(String charset) {
        this.charset = charset;
    }

    public String charset() {
        return charset;
    }
}
