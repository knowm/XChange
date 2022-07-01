package org.knowm.xchange.kucoin.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class WithdrawalResponse {

  /** Unique identity */
  private String id;
  /** Withdrawal address */
  private String address;
  /**
   * The note that is left on the withdrawal address. When you withdraw from KuCoin to other
   * platforms, you need to fill in memo(tag). If you don't fill in memo(tag), your withdrawal may
   * not be available.
   */
  private String memo;
  /** Currency */
  private String currency;
  /** Withdrawal amount */
  private BigDecimal amount;
  /** Withdrawal fee */
  private BigDecimal fee;
  /** Wallet Txid */
  private String walletTxId;
  /** Internal withdrawal or not */
  private boolean isInner;
  /** status: PROCESSING, WALLET_PROCESSING, SUCCESS, and FAILURE */
  private String status;
  /** Creation time */
  private Date createdAt;
  /** Update time */
  private Date updatedAt;
}
