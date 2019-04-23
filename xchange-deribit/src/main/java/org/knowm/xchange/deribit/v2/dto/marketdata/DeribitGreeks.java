package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitGreeks {

    @JsonProperty("vega") public BigDecimal vega;
    @JsonProperty("theta") public BigDecimal theta;
    @JsonProperty("rho") public BigDecimal rho;
    @JsonProperty("gamma") public BigDecimal gamma;
    @JsonProperty("delta") public BigDecimal delta;


    public BigDecimal getVega() {
        return vega;
    }

    public BigDecimal getTheta() {
        return theta;
    }

    public BigDecimal getRho() {
        return rho;
    }

    public BigDecimal getGamma() {
        return gamma;
    }

    public BigDecimal getDelta() {
        return delta;
    }
}
