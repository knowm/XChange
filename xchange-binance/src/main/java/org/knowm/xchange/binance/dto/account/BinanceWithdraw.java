package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BinanceWithdraw {

  private BigDecimal amount;
  private BigDecimal transactionFee;
  private String address;
  private String addressTag;
  private long successTime;
  private String txId;
  private String id;
  private String coin;
  private long applyTime;
  /** (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed) */
  private int status;
}
