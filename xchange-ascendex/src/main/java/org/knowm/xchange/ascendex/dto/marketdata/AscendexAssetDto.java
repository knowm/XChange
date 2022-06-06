package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AscendexAssetDto {

  private final String assetCode;

  private final String assetName;

  private final int precisionScale;

  private final int nativeScale;

  private final BigDecimal withdrawalFee;

  private final BigDecimal minWithdrawalAmt;

  private final AscendexAssetStatus status;

  public AscendexAssetDto(
      @JsonProperty("assetCode") String assetCode,
      @JsonProperty("assetName") String assetName,
      @JsonProperty("precisionScale") int precisionScale,
      @JsonProperty("nativeScale") int nativeScale,
      @JsonProperty("withdrawalFee") BigDecimal withdrawalFee,
      @JsonProperty("nimWithdrawalAmt") BigDecimal minWithdrawalAmt,
      @JsonProperty("status") AscendexAssetStatus status) {
    this.assetCode = assetCode;
    this.assetName = assetName;
    this.precisionScale = precisionScale;
    this.nativeScale = nativeScale;
    this.withdrawalFee = withdrawalFee;
    this.minWithdrawalAmt = minWithdrawalAmt;
    this.status = status;
  }

  public String getAssetCode() {
    return assetCode;
  }

  public String getAssetName() {
    return assetName;
  }

  public int getPrecisionScale() {
    return precisionScale;
  }

  public int getNativeScale() {
    return nativeScale;
  }

  public BigDecimal getWithdrawFee() {
    return withdrawalFee;
  }

  public BigDecimal getMinWithdrawalAmt() {
    return minWithdrawalAmt;
  }

  public AscendexAssetStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "AscendexAssetDto{"
        + "assetCode='"
        + assetCode
        + '\''
        + ", assetName='"
        + assetName
        + '\''
        + ", precisionScale="
        + precisionScale
        + ", nativeScale="
        + nativeScale
        + ", withdrawFee="
        + withdrawalFee
        + ", minWithdrawalAmt="
        + minWithdrawalAmt
        + ", status="
        + status
        + '}';
  }

  public enum AscendexAssetStatus {
    Normal,
    NoDeposit,
    NoTrading,
    NoWithdraw,
    InternalTrading,
    NoTransaction
  }
}
