package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitWithdrawalPriority {

    @JsonProperty("value") public float value;
    @JsonProperty("name") public String name;

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
