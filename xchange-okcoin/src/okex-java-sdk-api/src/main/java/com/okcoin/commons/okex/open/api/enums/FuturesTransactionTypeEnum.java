package com.okcoin.commons.okex.open.api.enums;

/**
 * Transaction type
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 15:49
 */
public enum FuturesTransactionTypeEnum {

    OPEN_LONG(1), OPEN_SHORT(2), CLOSE_LONG(3), CLOSE_SHORT(4),;

    private int code;

    FuturesTransactionTypeEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
