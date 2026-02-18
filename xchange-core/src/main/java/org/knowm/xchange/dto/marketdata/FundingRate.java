package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString
@Setter
@NoArgsConstructor
public class FundingRate {

  private Instrument instrument;
  private BigDecimal fundingRate1h;
  private BigDecimal fundingRate;
  private FundingRateInterval fundingRateInterval;
  private Date fundingRateDate;
  private long fundingRateEffectiveInMinutes;

  public FundingRate(
      @JsonProperty("instrument") Instrument instrument,
      @JsonProperty("fundingRate1h") BigDecimal fundingRate1h,
      @JsonProperty("fundingRate") BigDecimal fundingRate,
      @JsonProperty("fundingRateInterval") FundingRateInterval fundingRateInterval,
      @JsonProperty("fundingRateDate") Date fundingRateDate,
      @JsonProperty("fundingRateEffectiveInMinutes") long fundingRateEffectiveInMinutes) {
    this.instrument = instrument;
    this.fundingRate1h = fundingRate1h;
    this.fundingRate = fundingRate;
    this.fundingRateInterval = fundingRateInterval;
    this.fundingRateDate = fundingRateDate;
    this.fundingRateEffectiveInMinutes =
        (fundingRateEffectiveInMinutes == 0 && fundingRateDate != null)
            ? calculateFundingRateEffectiveInMinutes(fundingRateDate)
            : fundingRateEffectiveInMinutes;
  }

  public static class Builder {

    protected Instrument instrument;
    protected BigDecimal fundingRate1h;
    protected BigDecimal fundingRate;
    protected FundingRateInterval fundingRateInterval;
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

    public FundingRate.Builder fundingRate(BigDecimal fundingRate) {

      this.fundingRate = fundingRate;
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

    public FundingRate.Builder fundingRateInterval(FundingRateInterval fundingRateInterval) {
      this.fundingRateInterval = fundingRateInterval;
      return this;
    }

    public FundingRate build() {

      return new FundingRate(
          instrument,
          fundingRate1h,
          fundingRate,
          fundingRateInterval,
          fundingRateDate,
          (fundingRateEffectiveInMinutes == 0 && fundingRateDate != null)
              ? calculateFundingRateEffectiveInMinutes(fundingRateDate)
              : fundingRateEffectiveInMinutes);
    }
  }

  private static long calculateFundingRateEffectiveInMinutes(Date fundingRateDate) {
    return TimeUnit.MILLISECONDS.toMinutes(
        fundingRateDate.getTime() - Date.from(Instant.now()).getTime());
  }

  @Getter
  public enum FundingRateInterval {
    H1(1),
    H2(2),
    H4(4),
    H6(6),
    H8(8);
    private final int hours;

    FundingRateInterval(int hours) {
      this.hours = hours;
    }
  }
}
