package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class AscendexAssetDto {

  private final String assetCode;

  private final String assetName;

  private final int precisionScale;

  private final int nativeScale;
  /**
   * v2 remove
   */
  private final BigDecimal withdrawalFee;
  private final BigDecimal minWithdrawalAmt;
  private final AscendexAssetStatus status;
  /**
   * v2 add
   */
  private final List<AscendexAssetBlockChain> blockChain;


  public AscendexAssetDto(
          @JsonProperty("assetCode") String assetCode,
          @JsonProperty("assetName") String assetName,
          @JsonProperty("precisionScale") int precisionScale,
          @JsonProperty("nativeScale") int nativeScale,
          @JsonProperty("withdrawalFee") BigDecimal withdrawalFee,
          @JsonProperty("nimWithdrawalAmt") BigDecimal minWithdrawalAmt,
          @JsonProperty("status") AscendexAssetStatus status,
          @JsonProperty("blockChain") List<AscendexAssetBlockChain> blockChain) {
    this.assetCode = assetCode;
    this.assetName = assetName;
    this.precisionScale = precisionScale;
    this.nativeScale = nativeScale;
    this.withdrawalFee = withdrawalFee;
    this.minWithdrawalAmt = minWithdrawalAmt;
    this.status = status;
    this.blockChain = blockChain;
  }
  public List<AscendexAssetBlockChain> getBlockChain() {
    return blockChain;
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
    if (blockChain!=null&&blockChain.size()!=0){
      return blockChain.get(0).getWithdrawFee();
    }
    return withdrawalFee;
  }

  public BigDecimal getMinWithdrawalAmt() {
    if (blockChain!=null&&blockChain.size()!=0){
      return blockChain.get(0).getMinWithdrawal();
    }
    return minWithdrawalAmt;
  }

  public AscendexAssetStatus getStatus() {
    return status==null?AscendexAssetStatus.Normal:status;
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

  public static class AscendexAssetBlockChain {
    private String	chainName;
    private BigDecimal withdrawFee;
    private Boolean	allowDeposit;
    private Boolean	allowWithdraw;
    private BigDecimal	minDepositAmt;
    private BigDecimal	minWithdrawal;
    private Integer	numConfirmations;

    @Override
    public String toString() {
      return "AscendexBlockChainDto{" +
              "chainName='" + chainName + '\'' +
              ", withdrawFee=" + withdrawFee +
              ", allowDeposit=" + allowDeposit +
              ", allowWithdraw=" + allowWithdraw +
              ", minDepositAmt=" + minDepositAmt +
              ", minWithdrawal=" + minWithdrawal +
              ", numConfirmations=" + numConfirmations +
              '}';
    }

    public String getChainName() {
      return chainName;
    }

    public void setChainName(String chainName) {
      this.chainName = chainName;
    }

    public BigDecimal getWithdrawFee() {
      return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
      this.withdrawFee = withdrawFee;
    }

    public Boolean getAllowDeposit() {
      return allowDeposit;
    }

    public void setAllowDeposit(Boolean allowDeposit) {
      this.allowDeposit = allowDeposit;
    }

    public Boolean getAllowWithdraw() {
      return allowWithdraw;
    }

    public void setAllowWithdraw(Boolean allowWithdraw) {
      this.allowWithdraw = allowWithdraw;
    }

    public BigDecimal getMinDepositAmt() {
      return minDepositAmt;
    }

    public void setMinDepositAmt(BigDecimal minDepositAmt) {
      this.minDepositAmt = minDepositAmt;
    }

    public BigDecimal getMinWithdrawal() {
      return minWithdrawal;
    }

    public void setMinWithdrawal(BigDecimal minWithdrawal) {
      this.minWithdrawal = minWithdrawal;
    }

    public Integer getNumConfirmations() {
      return numConfirmations;
    }

    public void setNumConfirmations(Integer numConfirmations) {
      this.numConfirmations = numConfirmations;
    }
  }
}
