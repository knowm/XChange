package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitWithdrawalPriority {

    @JsonProperty("value") public BigDecimal value;
    @JsonProperty("name") public String name;

    public BigDecimal getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
