package org.knowm.xchange.coinmarketcap.pro.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinMarketCapException extends RuntimeException {

    private final int errorCode;

    public CoinMarketCapException(
            @JsonProperty("error_code") int errorCode,
            @JsonProperty("error_message") String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
