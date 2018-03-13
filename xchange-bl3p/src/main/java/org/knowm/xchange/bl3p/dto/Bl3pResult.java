package org.knowm.xchange.bl3p.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bl3pResult<DataType> {

    @JsonProperty("result")
    private String result;

    @JsonProperty("data")
    private DataType data;

    public String getResult() {
        return result;
    }

    public DataType getData() {
        return data;
    }
}
