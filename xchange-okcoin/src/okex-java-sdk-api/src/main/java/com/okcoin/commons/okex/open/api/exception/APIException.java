package com.okcoin.commons.okex.open.api.exception;

/**
 * API Exception
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/8 19:59
 */
public class APIException extends RuntimeException {

    private int code;

    public APIException(String message) {
        super(message);
    }

    public APIException(int code, String message) {
        super(message);
        this.code = code;
    }


    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        if (this.code != 0) {
            return this.code + " : " + super.getMessage();
        }
        return super.getMessage();
    }
}
