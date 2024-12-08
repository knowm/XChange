package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BitmexAsset {

  @JsonProperty("asset")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency asset;

  @JsonProperty("name")
  private String name;

  @JsonProperty("scale")
  private Integer scale;

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("isMarginCurrency")
  private Boolean marginCurrency;

  @JsonProperty("minDepositAmount")
  private BigDecimal minDepositAmount;

  @JsonProperty("minWithdrawalAmount")
  private BigDecimal minWithdrawalAmount;

  @JsonProperty("maxWithdrawalAmount")
  private BigDecimal maxWithdrawalAmount;

  @JsonProperty("memoRequired")
  private Boolean memoRequired;

  @JsonProperty("networks")
  List<Network> networks;


  /**
   * @return Scaled value
   */
  public BigDecimal getMinDepositAmount() {
    if (minDepositAmount == null || scale == null || scale <= 0) {
      return null;
    }
    return minDepositAmount.scaleByPowerOfTen(-scale);
  }


  /**
   * @return Scaled value
   */
  public BigDecimal getMinWithdrawalAmount() {
    if (minWithdrawalAmount == null || scale == null || scale <= 0) {
      return null;
    }
    return minWithdrawalAmount.scaleByPowerOfTen(-scale);
  }

  /**
   * @return Scaled value
   */
  public BigDecimal getMaxWithdrawalAmount() {
    if (maxWithdrawalAmount == null || scale == null || scale <= 0) {
      return null;
    }
    return maxWithdrawalAmount.scaleByPowerOfTen(-scale);
  }



  @Data
  @Builder
  @Jacksonized
  public static class Network {

    @JsonIgnore
    Integer assetScale;

    @JsonProperty("asset")
    private String id;

    @JsonProperty("tokenAddress")
    private String tokenAddress;

    @JsonProperty("depositEnabled")
    private Boolean depositEnabled;

    @JsonProperty("withdrawalEnabled")
    private Boolean withdrawalEnabled;

    @JsonProperty("withdrawalFee")
    private BigDecimal withdrawalFee;

    @JsonProperty("minFee")
    private BigDecimal minFee;

    @JsonProperty("maxFee")
    private BigDecimal maxFee;

    /**
     * @return Scaled value
     */
    public BigDecimal getWithdrawalFee() {
      if (withdrawalFee == null || assetScale == null || assetScale <= 0) {
        return null;
      }
      return withdrawalFee.scaleByPowerOfTen(-assetScale);
    }

    /**
     * @return Scaled value
     */
    public BigDecimal getMinFee() {
      if (minFee == null || assetScale == null || assetScale <= 0) {
        return null;
      }
      return minFee.scaleByPowerOfTen(-assetScale);
    }

    /**
     * @return Scaled value
     */
    public BigDecimal getMaxFee() {
      if (maxFee == null || assetScale == null || assetScale <= 0) {
        return null;
      }
      return maxFee.scaleByPowerOfTen(-assetScale);
    }
  }
}
