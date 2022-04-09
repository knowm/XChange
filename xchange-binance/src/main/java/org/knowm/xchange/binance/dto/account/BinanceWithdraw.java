package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BinanceWithdraw {

  private String address;
  private String addressTag;
  private BigDecimal amount;
  private String applyTime;
  private String coin;
  private String id;
  private String withdrawOrderId;
  private String network;
  private int transferType; // 1 for internal transfer, 0 for external transfer
  /** (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed) */
  private int status;

  private BigDecimal transactionFee;
  private String txId;
}
