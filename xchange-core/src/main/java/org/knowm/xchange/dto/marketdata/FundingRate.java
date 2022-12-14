package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@ToString
public class FundingRate {

    private final Instrument instrument;
    private final BigDecimal fundingRate1h;
    private final BigDecimal fundingRate8h;
    private final Date fundingRateDate;
    private final long fundingRateEffectiveInMinutes;

    public FundingRate(
            @JsonProperty("instrument") Instrument instrument,
            @JsonProperty("fundingRate1h") BigDecimal fundingRate1h,
            @JsonProperty("fundingRate8h") BigDecimal fundingRate8h,
            @JsonProperty("fundingRateDate") Date fundingRateDate,
            @JsonProperty("fundingRateEffectiveInMinutes") long fundingRateEffectiveInMinutes) {
        this.instrument = instrument;
        this.fundingRate1h = fundingRate1h;
        this.fundingRate8h = fundingRate8h;
        this.fundingRateDate = fundingRateDate;
        this.fundingRateEffectiveInMinutes = (fundingRateEffectiveInMinutes == 0 && fundingRateDate != null)
                ? calculateFundingRateEffectiveInMinutes(fundingRateDate)
                : fundingRateEffectiveInMinutes;
    }

    public static class Builder {

        protected Instrument instrument;
        protected BigDecimal fundingRate1h;
        protected BigDecimal fundingRate8h;
        protected Date fundingRateDate;
        protected long fundingRateEffectiveInMinutes;

        public FundingRate.Builder instrument(Instrument instrument) {

            this.instrument = instrument;
            return this;
        }

        public FundingRate.Builder fundingRate1h(BigDecimal fundingRate1h) {

            this.fundingRate1h = fundingRate1h;
            return this;
        }

        public FundingRate.Builder fundingRate8h(BigDecimal fundingRate8h) {

            this.fundingRate8h = fundingRate8h;
            return this;
        }

        public FundingRate.Builder fundingRateDate(Date fundingRateDate) {

            this.fundingRateDate = fundingRateDate;
            return this;
        }

        public FundingRate.Builder fundingRateEffectiveInMinutes(long fundingRateEffectiveInMinutes) {
            this.fundingRateEffectiveInMinutes = fundingRateEffectiveInMinutes;
            return this;
        }

        public FundingRate build() {

            return new FundingRate(
                    instrument,
                    fundingRate1h,
                    fundingRate8h,
                    fundingRateDate,
                    (fundingRateEffectiveInMinutes == 0 && fundingRateDate != null)
                            ? calculateFundingRateEffectiveInMinutes(fundingRateDate)
                            : fundingRateEffectiveInMinutes
            );
        }
    }

    private static long calculateFundingRateEffectiveInMinutes(Date fundingRateDate){
        return TimeUnit.MILLISECONDS.toMinutes(fundingRateDate.getTime()-Date.from(Instant.now()).getTime());
    }
}
