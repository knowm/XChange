package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@ToString
public class FundingRate {

    private final Instrument instrument;
    private final BigDecimal fundingRate1h;
    private final BigDecimal fundingRate8h;
    private final Date fundingRateTime;
    private final Date fundingRateEffectiveInMinutes;

    public FundingRate(
            @JsonProperty("instrument") Instrument instrument,
            @JsonProperty("fundingRate1h") BigDecimal fundingRate1h,
            @JsonProperty("fundingRate8h") BigDecimal fundingRate8h,
            @JsonProperty("fundingRateTime") Date fundingRateTime,
            @JsonProperty("fundingRateEffectiveInMinutes") Date fundingRateEffectiveInMinutes) {
        this.instrument = instrument;
        this.fundingRate1h = fundingRate1h;
        this.fundingRate8h = fundingRate8h;
        this.fundingRateTime = fundingRateTime;
        this.fundingRateEffectiveInMinutes = fundingRateEffectiveInMinutes;
    }
}
