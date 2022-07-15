package org.knowm.xchange.ascendex.dto.marketdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexAssetDto {

  private String assetCode;

  private String assetName;

  private int precisionScale;

  private int nativeScale;
  /**
   * v2 remove
   */
  private BigDecimal withdrawalFee;
  private BigDecimal minWithdrawalAmt;
  private AscendexAssetStatus status;
  /**
   * v2 add
   */
  private List<AscendexAssetBlockChain> blockChain;



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


  public enum AscendexAssetStatus {
    Normal,
    NoDeposit,
    NoTrading,
    NoWithdraw,
    InternalTrading,
    NoTransaction
  }
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AscendexAssetBlockChain {
    private String	chainName;
    private BigDecimal withdrawFee;
    private Boolean	allowDeposit;
    private Boolean	allowWithdraw;
    private BigDecimal	minDepositAmt;
    private BigDecimal	minWithdrawal;
    private Integer	numConfirmations;

  }
}
