package com.okcoin.commons.okex.open.api.enums;

/**
 * Http Status  <br/>
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/2/5 20:38
 */
public enum HttpStatusEnum {

    OK(200, "OK", "The request is Successful."),
    BAD_REQUEST(400, "Bad Request", "Invalid request format."),
    UNAUTHORIZED(401, "Unauthorized", "Invalid authorization."),
    FORBIDDEN(403, "Forbidden", "You do not have access to the requested resource."),
    NOT_FOUND(404, "Not Found", "Request resource path error."),
    TOO_MANY_REQUESTS(429, "Too Many Requests", "When a rate limit is exceeded."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "We had a problem with our server."),;

    private int status;
    private String message;
    private String comment;

    HttpStatusEnum(int status, String message, String comment) {
        this.status = status;
        this.message = message;
        this.comment = comment;
    }

    public int status() {
        return status;
    }

    public String message() {
        return message;
    }

    public String comment() {
        return comment;
    }
}
