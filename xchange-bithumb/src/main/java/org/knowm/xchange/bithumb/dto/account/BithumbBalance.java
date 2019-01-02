package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BithumbBalance {

    private final BigDecimal totalKrw;
    private final BigDecimal inUseKrw;
    private final BigDecimal availableKrw;
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public BithumbBalance(
            @JsonProperty("total_krw") BigDecimal totalKrw,
            @JsonProperty("in_use_krw") BigDecimal inUseKrw,
            @JsonProperty("available_krw") BigDecimal availableKrw
            ) {
        this.totalKrw = totalKrw;
        this.inUseKrw = inUseKrw;
        this.availableKrw = availableKrw;
    }

    public BigDecimal getTotalKrw() {
        return totalKrw;
    }

    public BigDecimal getInUseKrw() {
        return inUseKrw;
    }

    public BigDecimal getAvailableKrw() {
        return availableKrw;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "BithumbBalance{" +
                "totalKrw=" + totalKrw +
                ", inUseKrw=" + inUseKrw +
                ", availableKrw=" + availableKrw +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
