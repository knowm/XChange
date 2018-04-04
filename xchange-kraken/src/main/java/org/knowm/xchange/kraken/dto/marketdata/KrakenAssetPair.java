package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class KrakenAssetPair {

  private final String altName;
  private final String classBase;
  private final String base;
  private final String classQuote;
  private final String quote;
  private final String volumeLotSize;
  private final int pairScale;
  private final int volumeLotScale;
  private final BigDecimal volumeMultiplier;
  private final List<String> leverage_buy;
  private final List<String> leverage_sell;
  private final List<KrakenFee> fees;
  private final List<KrakenFee> fees_maker;
  private final String feeVolumeCurrency;
  private final BigDecimal marginCall;
  private final BigDecimal marginStop;

  /**
   * Constructor
   *
   * @param altName
   * @param classBase
   * @param base
   * @param classQuote
   * @param quote
   * @param volumeLotSize
   * @param pairScale
   * @param volumeLotScale
   * @param volumeMultiplier
   * @param leverage
   * @param fees
   * @param feeVolumeCurrency
   * @param marginCall
   * @param marginStop
   */
  public KrakenAssetPair(
      @JsonProperty("altname") String altName,
      @JsonProperty("aclass_base") String classBase,
      @JsonProperty("base") String base,
      @JsonProperty("aclass_quote") String classQuote,
      @JsonProperty("quote") String quote,
      @JsonProperty("lot") String volumeLotSize,
      @JsonProperty("pair_decimals") int pairScale,
      @JsonProperty("lot_decimals") int volumeLotScale,
      @JsonProperty("lot_multiplier") BigDecimal volumeMultiplier,
      @JsonProperty("fees") List<KrakenFee> fees,
      @JsonProperty("fees_maker") List<KrakenFee> fees_maker,
      @JsonProperty("fee_volume_currency") String feeVolumeCurrency,
      @JsonProperty("margin_call") BigDecimal marginCall,
      @JsonProperty("margin_stop") BigDecimal marginStop,
      @JsonProperty("leverage_buy") List<String> leverage_buy,
      @JsonProperty("leverage_sell") List<String> leverage_sell) {

    this.altName = altName;
    this.classBase = classBase;
    this.base = base;
    this.classQuote = classQuote;
    this.quote = quote;
    this.volumeLotSize = volumeLotSize;
    this.pairScale = pairScale;
    this.volumeLotScale = volumeLotScale;
    this.volumeMultiplier = volumeMultiplier;
    this.fees = fees;
    this.fees_maker = fees_maker;
    this.leverage_buy = leverage_buy;
    this.leverage_sell = leverage_sell;
    this.feeVolumeCurrency = feeVolumeCurrency;
    this.marginCall = marginCall;
    this.marginStop = marginStop;
  }

  public String getAltName() {

    return altName;
  }

  public String getClassBase() {

    return classBase;
  }

  public String getBase() {

    return base;
  }

  public String getClassQuote() {

    return classQuote;
  }

  public String getQuote() {

    return quote;
  }

  public String getVolumeLotSize() {

    return volumeLotSize;
  }

  public int getPairScale() {

    return pairScale;
  }

  public int getVolumeLotScale() {

    return volumeLotScale;
  }

  public BigDecimal getVolumeMultiplier() {

    return volumeMultiplier;
  }

  public List<String> getLeverage_buy() {

    return leverage_buy;
  }

  public List<String> getLeverage_sell() {

    return leverage_sell;
  }

  public List<KrakenFee> getFees() {

    return fees;
  }

  public List<KrakenFee> getFees_maker() {

    return fees_maker;
  }

  public String getFeeVolumeCurrency() {

    return feeVolumeCurrency;
  }

  public BigDecimal getMarginCall() {

    return marginCall;
  }

  public BigDecimal getMarginStop() {

    return marginStop;
  }

  @Override
  public String toString() {

    return "KrakenAssetPairInfo [altName="
        + altName
        + ", classBase="
        + classBase
        + ", base="
        + base
        + ", classQuote="
        + classQuote
        + ", quote="
        + quote
        + ", volumeLotSize="
        + volumeLotSize
        + ", pairScale="
        + pairScale
        + ", volumeLotScale="
        + volumeLotScale
        + ", volumeMultiplier="
        + volumeMultiplier
        + ", leverage_buy="
        + leverage_buy
        + ", leverage_sell="
        + leverage_sell
        + ", fees="
        + fees
        + ", feeVolumeCurrency="
        + feeVolumeCurrency
        + ", marginCall="
        + marginCall
        + ", marginStop="
        + marginStop
        + "]";
  }
}
