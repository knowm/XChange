package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class GeminiTrailingVolumeResponse {
  @JsonProperty("maker_fee_bps")
  public BigDecimal MakerFeeBPS;

  @JsonProperty("taker_fee_bps")
  public BigDecimal TakerFeeBPS;

  @JsonProperty("auction_fee_bps")
  public BigDecimal AuctionFeeBPS;

  @JsonProperty("notional_30d_volume")
  public BigDecimal Notional30DayVolume;

  @JsonProperty("last_updated_ms")
  public BigInteger LastUpdatedMS;

  @JsonProperty("account_id")
  public long AccountID;

  @JsonProperty("date")
  public Date Date;
  
  public static class NotionalOneDayVolume {
    @JsonProperty("date")
    public Date Date;
    @JsonProperty("notional_volume")
    public BigDecimal NotionalVolume;
  }

  @JsonProperty("notional_1d_volume")
  public NotionalOneDayVolume[] VolumesPerDay;

}