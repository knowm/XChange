package com.xeiam.xchange.bitstamp.dto.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gnandiga
 */
public class BitStampGenericResponse {
    private final String error;
    private final String result;


    public BitStampGenericResponse(@JsonProperty("result") String result, @JsonProperty("error") String error) {
        this.error = error;
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }
}
