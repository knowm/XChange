package com.okcoin.commons.okex.open.api.bean.spot.result;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    private final T data;

    private ResponseResult(final T data) {
        this.data = data;
    }

    public static ResponseResult success() {
        return ResponseResult.success(new Object());
    }

    public static <T> ResponseResult<T> success(final T data) {
        return ResponseResult.build(data);
    }

    public static <T> ResponseResult<T> build(final T data) {
        return new ResponseResult(data);
    }
}
