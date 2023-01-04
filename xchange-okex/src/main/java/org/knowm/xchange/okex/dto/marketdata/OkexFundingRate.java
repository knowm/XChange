package org.knowm.xchange.okex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OkexFundingRate {

    private final String instType;
    private final String instId;
    private final BigDecimal fundingRate;
    private final BigDecimal nextFundingRate;
    private final Date fundingTime;
    private final Date nextFundingTime;

    public OkexFundingRate(
            @JsonProperty("instType") String instType,
            @JsonProperty("instId") String instId,
            @JsonProperty("fundingRate") BigDecimal fundingRate,
            @JsonProperty("nextFundingRate") BigDecimal nextFundingRate,
            @JsonProperty("fundingTime") Date fundingTime,
            @JsonProperty("nextFundingTime") Date nextFundingTime) {
        this.instType = instType;
        this.instId = instId;
        this.fundingRate = fundingRate;
        this.nextFundingRate = nextFundingRate;
        this.fundingTime = fundingTime;
        this.nextFundingTime = nextFundingTime;
    }
}
