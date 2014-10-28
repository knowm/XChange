package com.xeiam.xchange.bitstamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.*;

public class BitstampException extends HttpStatusExceptionSupport {
    public BitstampException(@JsonProperty("error") String error) {
        super(error);
    }
}
