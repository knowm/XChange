package com.xeiam.xchange.coinmate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Martin Stachon
 * @param <T>
 */
public class CoinmateBaseResponse<T> {
    private final boolean error;
    private final String errorMessage;
    private final T data;
    
    @JsonCreator
    public CoinmateBaseResponse(@JsonProperty("error") boolean error,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") T data) {
        
        this.error = error;
        this.errorMessage = errorMessage;
        this.data = data;
        
    }
    
    public boolean isError() {
        return error;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public T getData() {
        return data;
    }

}
