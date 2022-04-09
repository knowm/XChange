package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BinanceDeposit {

  private BigDecimal amount;
  private String coin;
  private String network;
  /** (0:pending,1:success) */
  private int status;

  private String address;
  private String addressTag;
  private String txId;
  private long insertTime;
  private int transferType; // 1 for internal transfer, 0 for external transfer
  private String confirmTimes;
}
