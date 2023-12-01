package org.knowm.xchange.kucoin.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class DepositResponse {

  /** Creation time of the database record */
  private Date createdAt;

  /** Deposit amount */
  private BigDecimal amount;

  /** Deposit address */
  private String address;

  /** Deposit fee */
  private BigDecimal fee;

  /**
   * The note which was left on the deposit address. When you withdraw from other platforms to the
   * KuCoin, you need to fill in memo(tag). If you do not fill memo (tag), your deposit may not be
   * available, please be cautious.
   */
  private String memo;

  /** Currency code */
  private String currency;

  /** Wallet Txid */
  private String walletTxId;

  /** status: PROCESSING, WALLET_PROCESSING, SUCCESS, and FAILURE */
  private String status;

  /** Internal deposit or not */
  private boolean isInner;

  /** Update time of the database record */
  private Date updatedAt;
}
