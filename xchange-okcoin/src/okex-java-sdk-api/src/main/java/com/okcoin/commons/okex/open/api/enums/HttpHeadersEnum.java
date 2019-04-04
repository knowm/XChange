package com.okcoin.commons.okex.open.api.enums;

/**
 * Http Headers Enum . <br/>
 * All REST requests must contain the following headers. <br/>
 * The api key and secret key will be randomly generated and provided by OKEX. <br/>
 * The Passphrase will be provided by you to further secure your API access. <br/>
 * OKEX stores the salted hash of your passphrase for verification, but cannot recover the passphrase if you forget it.<br/>
 * OKEX cursor pagination response headers.<br/>
 * Request page before (newer) and after (older) this pagination id,
 * and limit number of results per request. maximum 100. (default 100). <br/>
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/2/5 20:45
 */
public enum HttpHeadersEnum {

    OK_ACCESS_KEY("OK-ACCESS-KEY"),
    OK_ACCESS_SIGN("OK-ACCESS-SIGN"),
    OK_ACCESS_TIMESTAMP("OK-ACCESS-TIMESTAMP"),
    OK_ACCESS_PASSPHRASE("OK-ACCESS-PASSPHRASE"),

    OK_BEFORE("OK-BEFORE"),
    OK_AFTER("OK-AFTER"),
    OK_LIMIT("OK-LIMIT"),
    OK_FROM("OK-FROM"),
    OK_TO("OK-TO");

    private final String header;

    HttpHeadersEnum(final String header) {
        this.header = header;
    }

    public String header() {
        return this.header;
    }
}
