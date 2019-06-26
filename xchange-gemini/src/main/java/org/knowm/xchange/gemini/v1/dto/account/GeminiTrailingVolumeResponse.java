package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class GeminiTrailingVolumeResponse {
  @JsonProperty("web_maker_fee_bps")
  public BigDecimal webMakerFeeBps;

  @JsonProperty("web_taker_fee_bps")
  public BigDecimal webTakerFeeBps;

  @JsonProperty("web_auction_fee_bps")
  public BigDecimal webAuctionFeeBps;

  @JsonProperty("api_maker_fee_bps")
  public BigDecimal apiMakerFeeBPS;

  @JsonProperty("api_taker_fee_bps")
  public BigDecimal apiTakerFeeBPS;

  @JsonProperty("api_auction_fee_bps")
  public BigDecimal apiAuctionFeeBPS;

  @JsonProperty("fix_maker_fee_bps")
  public BigDecimal fixMakerFeeBps;

  @JsonProperty("fix_taker_fee_bps")
  public BigDecimal fixTakerFeeBps;

  @JsonProperty("fix_auction_fee_bps")
  public BigDecimal fixAuctionFeeBps;

  @JsonProperty("block_maker_fee_bps")
  public BigDecimal blockMakerFeeBps;

  @JsonProperty("block_taker_fee_bps")
  public BigDecimal blockTakerFeeBps;

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
