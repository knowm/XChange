package org.knowm.xchange.coindirect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coindirect.dto.errors.CoindirectError;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.util.List;

public class CoindirectException extends ExchangeException {
    private String error;
    private String message;
    private String path;
    private Long status;
    private List<CoindirectError> errorList;


    public CoindirectException(
            @JsonProperty("error") String error,
            @JsonProperty("message") String message,
            @JsonProperty("path") String path,
            @JsonProperty("status") Long status,
            @JsonProperty("errorList") List<CoindirectError> errorList
    ) {
        super(error);

        this.error = error;
        this.message = message;
        this.path = path;
        this.status = status;
        this.errorList = errorList;
    }

    public CoindirectException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        if(status == 403 || status == 401) {
            return "API-Key, Secret or server time is invalid";
        }
        return super.getMessage();
    }

}
