package org.knowm.xchange.kraken.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenVolumeFee {

  private final BigDecimal fee;
  private final BigDecimal minFee;
  private final BigDecimal maxFee;
  private final BigDecimal nextFee;
  private final BigDecimal nextVolume;
  private final BigDecimal tierVolume;

  /**
   * Constructor
   *
   * @param fee
   * @param minFee
   * @param maxFee
   * @param nextFee
   * @param nextVolume
   * @param tierVolume
   */
  public KrakenVolumeFee(@JsonProperty("fee") BigDecimal fee, @JsonProperty("minfee") BigDecimal minFee, @JsonProperty("maxfee") BigDecimal maxFee,
      @JsonProperty("nextfee") BigDecimal nextFee, @JsonProperty("nextvolume") BigDecimal nextVolume,
      @JsonProperty("tiervolume") BigDecimal tierVolume) {

    this.fee = fee;
    this.minFee = minFee;
    this.maxFee = maxFee;
    this.nextFee = nextFee;
    this.nextVolume = nextVolume;
    this.tierVolume = tierVolume;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getMinFee() {

    return minFee;
  }

  public BigDecimal getMaxFee() {

    return maxFee;
  }

  public BigDecimal getNextFee() {

    return nextFee;
  }

  public BigDecimal getNextVolume() {

    return nextVolume;
  }

  public BigDecimal getTierVolume() {

    return tierVolume;
  }

  @Override
  public String toString() {

    return "KrakenVolumeFee [fee=" + fee + ", minFee=" + minFee + ", maxFee=" + maxFee + ", nextFee=" + nextFee + ", nextVolume=" + nextVolume
        + ", tierVolume=" + tierVolume + "]";
  }

}
