package org.knowm.xchange.bitget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetResponse<T> {

    @JsonProperty("code")
    private String code;

    @JsonProperty("msg")
    private String message;

    @JsonProperty("requestTime")
    private Instant requestTime;

    @JsonProperty("data")
    private T data;

}
